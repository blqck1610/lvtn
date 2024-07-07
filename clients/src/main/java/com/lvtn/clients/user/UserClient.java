package com.lvtn.clients.user;

import com.lvtn.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(value = "USER-SERVICE", path = "/api/v1/user")
public interface UserClient {

    @GetMapping(value = "/get-user/{userId}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable(value = "userId") Integer userId);

    @GetMapping(value = "/auth")
    public ResponseEntity<UserForAuth> getUserForAuth(@RequestParam(value = "username") String username);

    @PostMapping(value = "/create-new-user")
    public ResponseEntity<String>  register(@RequestBody @Valid UserRegistrationRequest request);

    @PostMapping(value = "/create-new-user/admin")
    public ResponseEntity<String>  registerAdmin(@RequestBody @Valid UserRegistrationRequest request);

    @GetMapping(value = "/test")
    public ResponseEntity<String> test(@RequestParam(value = "test") String test);

}
