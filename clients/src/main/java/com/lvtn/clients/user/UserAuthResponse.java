package com.lvtn.clients.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthResponse {
    private Integer id;
    private String username;
    private String password;
    private String role;
}
