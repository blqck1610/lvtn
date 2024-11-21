package com.lvtn.utils.dto.user;

import lombok.Data;

import java.sql.Date;


/**
 * UpdateUserRequest
 * Version 1.0
 * Date: 23/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 23/10/2024        NGUYEN             create
 */
@Data
public class UpdateUserRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Date dateOfBirth;
}
