package com.livechat.backend.user.serviceimpl;

import com.livechat.backend.user.entity.UserEntity;
import com.livechat.backend.user.repository.UserRepository;
import com.livechat.backend.user.service.UserService;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @SuppressWarnings("null")
    public UserEntity save(UserEntity userEntity) {
        return Objects.requireNonNull(userRepository.save(userEntity), "Saved user must not be null");
    }
}
