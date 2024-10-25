package com.lvtn.utils.util;

import com.lvtn.utils.dto.ApiResponse;

/**
 * ResponseUtil
 * Version 1.0
 * Date: 24/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 24/10/2024        NGUYEN             create
 */
public class ResponseUtil {
    public static <T> ApiResponse<T> getApiResponse(int code, String message, T data) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }
}
