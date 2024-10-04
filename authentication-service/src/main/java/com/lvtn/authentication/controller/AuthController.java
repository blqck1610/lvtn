package com.lvtn.authentication.controller;


import com.lvtn.authentication.service.AuthService;
import com.lvtn.authentication.service.JwtService;
import com.lvtn.clients.user.UserClient;

import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.authenticate.AuthRequest;
import com.lvtn.utils.dto.authenticate.AuthResponse;
import com.lvtn.utils.dto.authenticate.TokenDto;
import com.lvtn.utils.dto.user.UserRegistrationRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final UserClient userClient;
    private final JwtService jwtService;

    @PostMapping(value = "/register")
    public ApiResponse<AuthResponse> register(@Valid @RequestBody UserRegistrationRequest request) {
        log.info("register user {}", request);
        return authService.registerNewUser(request);
    }


    @PostMapping(value = "/authenticate")
    public ApiResponse<AuthResponse> getToken(@Valid @RequestBody AuthRequest request) {
        log.info("authenticated: {}", request.getUsername());
        return authService.getToken(request);
    }

    @GetMapping(value = "/secured")
    public ResponseEntity<String> testSecured() {
        return ResponseEntity.ok("ok--secured");
    }

    @GetMapping(value = "/test")
    public String test() {
        return "ok";
    }

    @GetMapping(value = "/find-token-by-access-token")
    public ResponseEntity<TokenDto> findTokenByAcessToken(String token) {
        return ResponseEntity.ok(authService.findTokenByToken(token, "ACCESS_TOKEN"));
    }


    @GetMapping(value = "/refresh-Token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        authService.refreshToken(request, response);
    }

    @GetMapping(value = "/extract-all-claims")
    public Map<String, Object> extractAllClaims(@RequestParam(value = "token")  String token){
        return (Map<String, Object>) new HashMap<String, Object>(jwtService.extractAllClaims(token));
    }
    @GetMapping(value = "/is-token-valid")
    public Boolean isTokenValid(@RequestParam(value = "token")  String token, @RequestParam(value = "tokenType") String tokenType){
        return authService.isTokenValid(token, tokenType);
    }
    
    
    @GetMapping(value = "/is-token-expired")
    public Boolean isTokenExpired(@RequestParam(value = "token")  String token){
        return true;
    }

}
