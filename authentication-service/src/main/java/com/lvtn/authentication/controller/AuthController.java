package com.lvtn.authentication.controller;


import com.lvtn.authentication.service.AuthService;
import com.lvtn.authentication.util.CryptoUtil;
import com.lvtn.clients.user.*;
import com.lvtn.exception.BaseException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final UserClient userClient;

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationRequest request){
        UserForAuth user = userClient.getUserForAuth(request.getUsername());
        if(user != null){
            throw new BaseException(400, "username already registered");
        }
        log.info("register user {}" , user);
        return authService.register(request);
    }


    @PostMapping(value = "/authenticate")
    public ResponseEntity<Object> authenticate(@Valid @RequestBody AuthRequest request){
        UserForAuth user = userClient.getUserForAuth(request.getUsername());
        if(user == null){
            throw new BaseException(400, "username does not exist");
        }
        if(!CryptoUtil.encrypt(request.getPassword()).equals(user.getPassword())){
            throw new BaseException(400, "password does not match");
        }
        log.info("authenticated: {}", request.getUsername());
        return ResponseEntity.ok().body(authService.authenticate(user));
    }

}
