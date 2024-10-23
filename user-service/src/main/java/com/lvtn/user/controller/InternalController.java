package com.lvtn.user.controller;

import com.lvtn.user.service.UserService;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.request.authenticate.AuthRequest;
import com.lvtn.utils.dto.request.authenticate.RegisterRequest;
import com.lvtn.utils.dto.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * InternalController
 * Version 1.0
 * Date: 03/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 03/10/2024        NGUYEN             create
 */
@RestController
@RequestMapping(value = "/api/v1/internal/user")
@Slf4j
@RequiredArgsConstructor
public class InternalController {
    private final UserService userService;

    @PostMapping(value = "/authenticate")
    public ApiResponse<UserResponse> authenticate(@RequestBody AuthRequest request) {
        return userService.authenticate(request);
    }

    @PostMapping
    public ApiResponse<UserResponse> register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @GetMapping(value = "/get-by-username")
    public ApiResponse<UserResponse> findByUsername(@RequestParam String username) {
        return userService.getByUsername(username);
    }
}
