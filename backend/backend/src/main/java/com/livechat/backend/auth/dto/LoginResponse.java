package com.livechat.backend.auth.dto;

public record LoginResponse(
        AuthUserResponse user,
        String authMode
) {
}
