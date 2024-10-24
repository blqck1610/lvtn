package com.lvtn.utils.dto.request.authenticate;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
