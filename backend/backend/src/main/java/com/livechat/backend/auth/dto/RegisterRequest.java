package com.livechat.backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 100, message = "Username length must be between 3 and 100")
        String username,
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 255, message = "Password length must be between 8 and 255")
        String password
) {
}
