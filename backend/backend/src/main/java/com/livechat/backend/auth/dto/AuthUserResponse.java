package com.livechat.backend.auth.dto;

import java.time.OffsetDateTime;

public record AuthUserResponse(
        Long id,
        String email,
        String username,
        OffsetDateTime createdAt
) {
}
