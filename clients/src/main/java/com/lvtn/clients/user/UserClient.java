package com.lvtn.clients.user;


import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "USER-SERVICE", path = "/api/v1/user")
public interface UserClient {

    @GetMapping(value = "/get-user/{userId}")
    public UserDto getUserInfo(@PathVariable(value = "userId") Integer userId);

    @GetMapping(value = "/find-by-username/{username}")
    public UserDto findByUsername(@PathVariable(value = "username") String username);

    @GetMapping(value = "/auth")
    public UserAuthResponse getUserForAuth(@RequestParam(value = "username") String username);

    @PostMapping(value = "/create-new-user")
    public UserDto register(@RequestBody @Valid UserRegistrationRequest request);

    @PostMapping(value = "/create-new-user/admin")
    public UserDto  registerAdmin(@RequestBody @Valid UserRegistrationRequest request);

    @GetMapping(value = "/test")
    public ResponseEntity<String> test(@RequestParam(value = "test") String test);

}
