package com.lvtn.utils.dto.request.authenticate;


import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;

}
