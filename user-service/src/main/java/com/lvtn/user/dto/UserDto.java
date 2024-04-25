package com.lvtn.user.dto;

import com.lvtn.utils.Provider;
import com.lvtn.utils.Role;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private Role role;
    private Provider provider;
    private LocalDateTime creatAt;
}
