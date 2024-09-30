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

    @GetMapping(value = "/extract-all-claim")
    public Map<String, Object> extractAllClaim(@RequestParam(value = "token") String token);


}
