package com.livechat.backend.auth.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.livechat.backend.auth.dto.AuthUserResponse;
import com.livechat.backend.auth.dto.LoginRequest;
import com.livechat.backend.auth.dto.LoginResponse;
import com.livechat.backend.auth.dto.RegisterRequest;
import com.livechat.backend.common.exception.AppException;
import com.livechat.backend.user.entity.UserEntity;
import com.livechat.backend.user.service.UserService;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserService userService;

    private PasswordEncoder passwordEncoder;
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        authService = new AuthServiceImpl(userService, passwordEncoder);
    }

    @Test
    void registerShouldHashPasswordAndPersistUser() {
        RegisterRequest registerRequest = new RegisterRequest("test@example.com", "tester", "password123");
        UserEntity persisted = buildUser(1L, "test@example.com", "tester", passwordEncoder.encode("password123"));

        when(userService.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userService.existsByUsername("tester")).thenReturn(false);
        when(userService.save(any(UserEntity.class))).thenReturn(persisted);

        AuthUserResponse response = authService.register(registerRequest);

        assertEquals("test@example.com", response.email());
        verify(userService).save(any(UserEntity.class));
    }

    @Test
    void registerShouldFailWhenEmailAlreadyExists() {
        RegisterRequest registerRequest = new RegisterRequest("test@example.com", "tester", "password123");
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(buildUser(
                1L,
                "test@example.com",
                "tester",
                "hash"
        )));

        AppException exception = assertThrows(AppException.class, () -> authService.register(registerRequest));

        assertEquals("Email is already used", exception.getMessage());
        verify(userService, never()).existsByUsername(any(String.class));
    }

    @Test
    void registerShouldFailWhenUsernameAlreadyExists() {
        RegisterRequest registerRequest = new RegisterRequest("test@example.com", "tester", "password123");

        when(userService.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userService.existsByUsername("tester")).thenReturn(true);

        AppException exception = assertThrows(AppException.class, () -> authService.register(registerRequest));

        assertEquals("Username is already used", exception.getMessage());
        verify(userService, never()).save(any(UserEntity.class));
    }

    @Test
    void loginShouldReturnUserWhenCredentialsAreValid() {
        String password = "password123";
        UserEntity user = buildUser(1L, "test@example.com", "tester", passwordEncoder.encode(password));

        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        LoginResponse loginResponse = authService.login(new LoginRequest("test@example.com", password));

        assertEquals("PASSWORD_ONLY_TEMPORARY", loginResponse.authMode());
        assertEquals("test@example.com", loginResponse.user().email());
    }

    @Test
    void loginShouldFailWhenPasswordIsInvalid() {
        UserEntity user = buildUser(1L, "test@example.com", "tester", passwordEncoder.encode("password123"));
        LoginRequest invalidPasswordRequest = new LoginRequest("test@example.com", "bad-password");
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        AppException exception = assertThrows(
                AppException.class,
            () -> authService.login(invalidPasswordRequest)
        );

        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void loginShouldFailWhenUserNotFound() {
        LoginRequest unknownUserRequest = new LoginRequest("missing@example.com", "password123");
        when(userService.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        AppException exception = assertThrows(
                AppException.class,
            () -> authService.login(unknownUserRequest)
        );

        assertEquals("Invalid credentials", exception.getMessage());
        assertTrue(exception.getErrorCode().name().contains("INVALID_CREDENTIALS"));
    }

    private UserEntity buildUser(Long id, String email, String username, String passwordHash) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setEmail(email);
        userEntity.setUsername(username);
        userEntity.setPasswordHash(passwordHash);
        userEntity.setCreatedAt(OffsetDateTime.now());
        userEntity.setUpdatedAt(OffsetDateTime.now());
        return userEntity;
    }
}
