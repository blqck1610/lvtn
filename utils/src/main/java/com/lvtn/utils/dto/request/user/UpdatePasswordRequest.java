package com.lvtn.utils.dto.request.user;

import lombok.Data;

/**
 * UpdatePasswordRequest
 * Version 1.0
 * Date: 24/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 24/10/2024        NGUYEN             create
 */
@Data
public class UpdatePasswordRequest {
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
}
