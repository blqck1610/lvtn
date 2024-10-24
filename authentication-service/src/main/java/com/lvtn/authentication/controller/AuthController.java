package com.lvtn.authentication.controller;


import com.lvtn.authentication.service.AuthService;
import com.lvtn.utils.common.SuccessMessage;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.request.authenticate.AuthRequest;
import com.lvtn.utils.dto.request.authenticate.RefreshTokenRequest;
import com.lvtn.utils.dto.request.authenticate.RegisterRequest;
import com.lvtn.utils.dto.response.authenticate.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.lvtn.utils.constant.ApiEndpoint.*;
import static com.lvtn.utils.util.ResponseUtil.getApiResponse;

@RestController
@RequestMapping(value = BASE_API + AUTH)
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = REGISTER)
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return getApiResponse(HttpStatus.CREATED.value(), SuccessMessage.CREATED_SUCCESS.getMessage(), null);
    }

    @PostMapping(value = AUTHENTICATE)
    public ApiResponse<AuthResponse> getToken(@Valid @RequestBody AuthRequest request) {
        return getApiResponse(HttpStatus.OK.value(), SuccessMessage.AUTHENTICATE_SUCCESS.getMessage(), authService.getToken(request));
    }

    @PostMapping(value = REFRESH_TOKEN)
    public ApiResponse<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws IOException {
        return getApiResponse(HttpStatus.OK.value(), SuccessMessage.OK.getMessage(), authService.refreshToken(request));
    }
}
