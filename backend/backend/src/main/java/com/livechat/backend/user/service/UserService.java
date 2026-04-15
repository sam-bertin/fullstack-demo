package com.livechat.backend.user.service;

import com.livechat.backend.user.entity.UserEntity;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByUsername(String username);

    UserEntity save(UserEntity userEntity);
}
