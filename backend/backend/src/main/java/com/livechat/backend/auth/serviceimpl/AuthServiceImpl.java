package com.livechat.backend.auth.serviceimpl;

import com.livechat.backend.auth.dto.AuthUserResponse;
import com.livechat.backend.auth.dto.LoginRequest;
import com.livechat.backend.auth.dto.LoginResponse;
import com.livechat.backend.auth.dto.RegisterRequest;
import com.livechat.backend.auth.service.AuthService;
import com.livechat.backend.common.exception.AppException;
import com.livechat.backend.common.exception.ErrorCode;
import com.livechat.backend.user.entity.UserEntity;
import com.livechat.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String AUTH_MODE_PASSWORD_ONLY = "PASSWORD_ONLY_TEMPORARY";

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthUserResponse register(RegisterRequest registerRequest) {
        String normalizedEmail = registerRequest.email().trim().toLowerCase();
        String normalizedUsername = registerRequest.username().trim();

        if (userService.findByEmail(normalizedEmail).isPresent()) {
            throw new AppException("Email is already used", ErrorCode.RESOURCE_CONFLICT, HttpStatus.CONFLICT);
        }

        if (userService.existsByUsername(normalizedUsername)) {
            throw new AppException("Username is already used", ErrorCode.RESOURCE_CONFLICT, HttpStatus.CONFLICT);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(normalizedEmail);
        userEntity.setUsername(normalizedUsername);
        userEntity.setPasswordHash(passwordEncoder.encode(registerRequest.password()));

        UserEntity savedUser = userService.save(userEntity);
        return mapToAuthUserResponse(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity userEntity = userService.findByEmail(loginRequest.email().trim().toLowerCase())
                .orElseThrow(() -> new AppException(
                        "Invalid credentials",
                        ErrorCode.INVALID_CREDENTIALS,
                        HttpStatus.UNAUTHORIZED
                ));

        if (!passwordEncoder.matches(loginRequest.password(), userEntity.getPasswordHash())) {
            throw new AppException("Invalid credentials", ErrorCode.INVALID_CREDENTIALS, HttpStatus.UNAUTHORIZED);
        }

        return new LoginResponse(mapToAuthUserResponse(userEntity), AUTH_MODE_PASSWORD_ONLY);
    }

    private AuthUserResponse mapToAuthUserResponse(UserEntity userEntity) {
        return new AuthUserResponse(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getUsername(),
                userEntity.getCreatedAt()
        );
    }
}
