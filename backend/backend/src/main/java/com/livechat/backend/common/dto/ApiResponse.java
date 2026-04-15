package com.livechat.backend.common.dto;

public record ApiResponse<T>(boolean success, String message, T data, String errorCode) {

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, null);
    }

    public static ApiResponse<Void> failure(String message, String errorCode) {
        return new ApiResponse<>(false, message, null, errorCode);
    }
}
