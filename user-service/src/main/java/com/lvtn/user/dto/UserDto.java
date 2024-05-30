package com.lvtn.user.dto;

import com.lvtn.user.entity.Address;
import com.lvtn.utils.Provider;
import com.lvtn.utils.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private Address address;
    private Role role;
    private Provider provider;
    private LocalDateTime creatAt;
}
