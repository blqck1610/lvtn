package com.lvtn.authentication.controller;


import com.lvtn.authentication.service.AuthService;
import com.lvtn.authentication.service.imp.JwtService;
import com.lvtn.clients.user.UserClient;
import com.lvtn.utils.common.SuccessMessage;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.request.authenticate.AuthRequest;
import com.lvtn.utils.dto.request.authenticate.RegisterRequest;
import com.lvtn.utils.dto.response.authenticate.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.lvtn.utils.constant.ApiEndpoint.*;

@RestController
@RequestMapping(value = BASE_API + AUTH)
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final UserClient userClient;
    private final JwtService jwtService;

    @PostMapping(value = REGISTER)
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ApiResponse.<AuthResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message(SuccessMessage.CREATED_SUCCESS.getMessage())
                .data(null)
                .build();
    }

    @PostMapping(value = AUTHENTICATE)
    public ApiResponse<AuthResponse> getToken(@Valid @RequestBody AuthRequest request) {
        return ApiResponse.<AuthResponse>builder()
                .code(HttpStatus.OK.value())
                .message(SuccessMessage.AUTHENTICATE_SUCCESS.getMessage())
                .data(authService.getToken(request))
                .build();
    }

//    @PostMapping(value = REFRESH_TOKEN)
//    public ApiResponse<AuthResponse> refreshToken(HttpServletRequest request) throws IOException {
//        return ApiResponse.
//    }

    @GetMapping(value = EXTRACT_ALL_CLAIMS)
    public Map<String, Object> extractAllClaims(@RequestParam(value = "token") String token) {
        return (Map<String, Object>) new HashMap<String, Object>(jwtService.extractAllClaims(token));
    }
}
