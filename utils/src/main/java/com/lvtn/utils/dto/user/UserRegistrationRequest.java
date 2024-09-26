package com.lvtn.utils.dto.user;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRegistrationRequest {

    @NotEmpty(message = "username mustn't empty")
    private String username;
    @NotEmpty(message = "password mustn't empty")
    private String password;
    @NotEmpty(message = "password mustn't empty")
    private String email;

}
