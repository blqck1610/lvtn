package com.lvtn.user.service;

import com.lvtn.clients.user.Address;
import com.lvtn.clients.user.UserDto;
import com.lvtn.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {


    public UserDto fromUser(User user) {

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .address(new Address(user.getAddress().getAddress(), user.getAddress().getPhoneNumber()))

                .build();
    }
}
