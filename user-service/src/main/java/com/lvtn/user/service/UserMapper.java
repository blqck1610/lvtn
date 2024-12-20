package com.lvtn.user.service;


import com.lvtn.user.entity.User;
import com.lvtn.utils.dto.user.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {


    public UserResponse fromUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
