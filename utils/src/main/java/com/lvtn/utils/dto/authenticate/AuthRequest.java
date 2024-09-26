package com.lvtn.utils.dto.authenticate;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data

public class AuthRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
