package com.lvtn.user.controller;

import com.lvtn.user.service.UserService;
import com.lvtn.utils.common.ErrorCode;
import com.lvtn.utils.constant.Attribute;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.auth.AuthRequest;
import com.lvtn.utils.dto.auth.RegisterRequest;
import com.lvtn.utils.dto.user.UserResponse;
import com.lvtn.utils.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.lvtn.utils.constant.ApiEndpoint.*;

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
@RequestMapping(value = BASE_API + INTERNAL + USER)
@Slf4j
@RequiredArgsConstructor
public class InternalController {
    private final UserService userService;

    @PostMapping(value = AUTHENTICATE)
    public ApiResponse<UserResponse> authenticate(@RequestBody AuthRequest request) {
        return userService.authenticate(request);
    }

    @PostMapping
    public ApiResponse<UserResponse> register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @GetMapping(value = GET_BY_USERNAME)
    public ApiResponse<UserResponse> findByUsername(@PathVariable(Attribute.USERNAME) String username) {
        return userService.getByUsername(username);
    }

    @PostMapping(value = "/test")
    public ApiResponse<UserResponse> test(@RequestBody AuthRequest request) {
        if (request.getUsername().equals("admin"))
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), ErrorCode.BAD_REQUEST.getMessage());
        else {
            return userService.getByUsername(request.getUsername());
        }
    }
}
