package com.livechat.backend.user.service;

import com.livechat.backend.user.entity.UserEntity;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByEmail(String email);

    UserEntity save(UserEntity userEntity);
}
