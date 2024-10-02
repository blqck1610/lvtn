package com.lvtn.clients.authentication;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * AuthenticationClient
 * Version 1.0
 * Date: 26/09/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 26/09/2024        NGUYEN             create
 */
@FeignClient(value = "AUTH-SERVICE", path = "/api/v1/auth")
public interface AuthenticationClient {

    @GetMapping(value = "/extract-all-claims")
    Map<String, Object> extractAllClaims(@RequestParam(value = "token") String token);

    @GetMapping(value = "/test")
    public String test();

    @GetMapping(value = "/is-token-valid")
    public Boolean isTokenValid(@RequestParam(value = "token")  String token);

    @GetMapping(value = "/is-token-expired")
    public Boolean isTokenExpired(@RequestParam(value = "token")  String token);
}
