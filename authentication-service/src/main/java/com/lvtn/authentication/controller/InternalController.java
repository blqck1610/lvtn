package com.lvtn.authentication.controller;

import com.lvtn.authentication.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lvtn.utils.constant.ApiEndpoint.*;

/**
 * InternalController
 * Version 1.0
 * Date: 24/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 24/10/2024        NGUYEN             create
 */
@RestController
@RequestMapping(value = BASE_API + INTERNAL + AUTH)
@RequiredArgsConstructor
@Slf4j
public class InternalController {
    private final AuthService authService;
    @PostMapping(value = REVOKE_ALL_TOKEN)
    public void revokeAllToken(@RequestBody String userId){
        authService.revokeAllTokens(userId);
    }
}
