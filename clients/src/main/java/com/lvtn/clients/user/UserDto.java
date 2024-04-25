package com.lvtn.clients.user;

import com.lvtn.utils.Provider;
import com.lvtn.utils.Role;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private Role role;
    private Provider provider;
    private LocalDateTime creatAt;
}
