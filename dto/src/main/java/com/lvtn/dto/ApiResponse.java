package com.lvtn.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    private Integer code;
    private String message;
}
