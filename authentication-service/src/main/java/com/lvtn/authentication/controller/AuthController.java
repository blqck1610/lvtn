package com.lvtn.authentication.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvtn.authentication.service.AuthService;
import com.lvtn.authentication.service.JwtService;
import com.lvtn.clients.user.UserClient;
import com.lvtn.exception.BaseException;
import com.lvtn.utils.dto.authenticate.AuthRequest;
import com.lvtn.utils.dto.authenticate.AuthResponse;
import com.lvtn.utils.dto.authenticate.TokenDto;
import com.lvtn.utils.dto.user.UserDto;
import com.lvtn.utils.dto.user.UserRegistrationRequest;
import com.lvtn.utils.dto.user.UserV0;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
    public AuthResponse register(@Valid @RequestBody UserRegistrationRequest request) {
        UserV0 user = userClient.getUserForAuth(request.getUsername());
        if (user != null) {
            throw new BaseException(400, "username already registered");
        }
        log.info("register user {}", user);
        return authService.register(request);
    }


    @PostMapping(value = "/authenticate")
    public AuthResponse authenticate(@Valid @RequestBody AuthRequest request) {
        UserV0 user = userClient.getUserForAuth(request.getUsername());
        if (user == null) {
            throw new BaseException(400, "username does not exist");
        }
        if (BCrypt.checkpw(request.getPassword(), user.getPassword()))

            log.info("authenticated: {}", request.getUsername());
        return authService.authenticate(user);
    }

    @PostMapping(value = "/register/admin")
    public UserDto registerAdmin(@Valid @RequestBody UserRegistrationRequest request) {
        UserV0 user = userClient.getUserForAuth(request.getUsername());
        if (user != null) {
            throw new BaseException(400, "username already registered");
        }
        log.info("register user {}", user);
        return authService.registerAdmin(request);
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

    //    @GetMapping(value = "/refresh-Token")
//    public ResponseEntity<TokenDto> refreshToken(String refreshToken){
//
//        return ResponseEntity.ok(authService.refreshToken((refreshToken));
//    }
    @GetMapping(value = "/refresh-Token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        authService.refreshToken(request, response);
    }


    @GetMapping(value = "/extract-all-claims")
    public Map<String, Object> extractAllClaims(@RequestParam(value = "token")  String token){
        return (Map<String, Object>) new HashMap<String, Object>(jwtService.extractAllClaims(token));
    }
    @GetMapping(value = "/is-token-valid")
    public Boolean isTokenValid(@RequestParam(value = "token")  String token){
        return authService.isTokenValid(token, "ACCESS_TOKEN");
    }
    @GetMapping(value = "/is-token-expired")
    public Boolean isTokenExpired(@RequestParam(value = "token")  String token){
        return true;
    }

}
