package com.lvtn.utils.dto.response.authenticate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TokenDto
 * Version 1.0
 * Date: 26/09/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 26/09/2024        NGUYEN             create
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
