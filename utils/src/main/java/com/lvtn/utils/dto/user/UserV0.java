package com.lvtn.utils.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserV0 {
    private Integer id;
    private String username;
    private String password;
    private String role;
}
