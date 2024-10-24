package com.lvtn.authentication.service.imp;

import com.lvtn.authentication.entity.Token;
import com.lvtn.utils.dto.response.authenticate.TokenDto;

import org.springframework.stereotype.Service;

/**
 * TokenMapper
 * Version 1.0
 * Date: 26/09/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 26/09/2024        NGUYEN             create
 */

@Service
public class TokenMapper {
    TokenDto fromToken(Token token){
        return TokenDto.builder()
                .refreshToken(token.getRefreshToken())
                .accessToken(token.getAccessToken())
                .build();

    }
}
