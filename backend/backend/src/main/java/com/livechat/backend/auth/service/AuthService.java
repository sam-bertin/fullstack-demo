package com.livechat.backend.auth.service;

import com.livechat.backend.auth.dto.AuthUserResponse;
import com.livechat.backend.auth.dto.LoginRequest;
import com.livechat.backend.auth.dto.LoginResponse;
import com.livechat.backend.auth.dto.RegisterRequest;

public interface AuthService {

    AuthUserResponse register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);
}
