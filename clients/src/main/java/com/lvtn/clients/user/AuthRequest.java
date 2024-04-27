package com.lvtn.clients.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data

public class AuthRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
