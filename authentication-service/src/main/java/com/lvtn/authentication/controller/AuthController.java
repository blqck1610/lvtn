package com.lvtn.authentication.controller;


import com.lvtn.authentication.dto.AuthResponse;
import com.lvtn.authentication.service.AuthService;
import com.lvtn.clients.user.AuthRequest;
import com.lvtn.clients.user.UserRegistrationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationRequest request){
        return authService.register(request);
    }
    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request){
        return ResponseEntity.ok().body(authService.authenticate(request));
    }

}
