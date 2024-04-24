package com.lvtn.user;


import lombok.Data;

import java.time.LocalDateTime;

@Data

public class UserRegistrationRequest {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime creatAt;
}
