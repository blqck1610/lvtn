package com.lvtn.user.controller;

import com.lvtn.user.service.UserService;
import com.lvtn.utils.ErrorCode;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.authenticate.AuthRequest;
import com.lvtn.utils.dto.user.UserDto;
import com.lvtn.utils.dto.user.UserRegistrationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ApiResponse<UserDto> authenticate(@RequestBody AuthRequest request) {

        return new ApiResponse<>(HttpStatus.OK, userService.authenticate(request));
    }

    @PostMapping(value = "/create-new-user")
    public ApiResponse<UserDto> register(@RequestBody @Valid UserRegistrationRequest request) {
        return new ApiResponse<>(HttpStatus.OK, "register new user successfully", userService.registerNewUser(request));
    }

    @GetMapping(value = "/get-by-username")
    public ApiResponse<UserDto> findByUsername(@RequestParam(value = "username") String username) {

        return new ApiResponse<>(HttpStatus.OK, "OK", userService.getByUsername(username));
    }


}
