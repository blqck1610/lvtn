package com.lvtn.authentication.controller;


import com.lvtn.authentication.service.AuthService;
import com.lvtn.clients.user.UserClient;
import com.lvtn.exception.BaseException;
import com.lvtn.utils.dto.authenticate.AuthRequest;
import com.lvtn.utils.dto.authenticate.AuthResponse;
import com.lvtn.utils.dto.user.UserDto;
import com.lvtn.utils.dto.user.UserRegistrationRequest;
import com.lvtn.utils.dto.user.UserV0;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
    public AuthResponse register(@Valid @RequestBody UserRegistrationRequest request){
        UserV0 user = userClient.getUserForAuth(request.getUsername());
        if(user != null){
            throw new BaseException(400, "username already registered");
        }
        log.info("register user {}" , user);
        return authService.register(request);
    }


    @PostMapping(value = "/authenticate")
    public AuthResponse authenticate(@Valid @RequestBody AuthRequest request){
        UserV0 user = userClient.getUserForAuth(request.getUsername());
        if(user == null){
            throw new BaseException(400, "username does not exist");
        }
        if(BCrypt.checkpw(request.getPassword(), user.getPassword()))

        log.info("authenticated: {}", request.getUsername());
        return authService.authenticate(user);
    }

    @PostMapping(value = "/register/admin")
    public UserDto registerAdmin(@Valid @RequestBody UserRegistrationRequest request){
        UserV0 user = userClient.getUserForAuth(request.getUsername());
        if(user != null){
            throw new BaseException(400, "username already registered");
        }
        log.info("register user {}" , user);
        return authService.registerAdmin(request);
    }

}
