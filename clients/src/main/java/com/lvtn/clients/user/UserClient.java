package com.lvtn.clients.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "USER", path = "/api/v1/user")
public interface UserClient {

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationRequest request);

    @PostMapping(value ="/authenticate")
    public UserDto authenticate(@RequestBody @Valid AuthRequest request);

    @GetMapping(value = "/{username}/info")
    public UserDto getUserInfo(@PathVariable(value = "username") String username);

}
