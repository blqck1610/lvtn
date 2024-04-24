package com.lvtn.user;

import com.lvtn.utils.Provider;
import com.lvtn.utils.Role;

import java.time.LocalDateTime;

public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private Role role;
    private Provider provider;
    private LocalDateTime creatAt;
}
