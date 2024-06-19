package com.lvtn.clients.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(value = "USER-SERVICE", path = "/api/v1/user")
public interface UserClient {

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable(value = "userId") Integer userId);

    @GetMapping(value = "/auth")
    public UserForAuth getUserForAuth(String username);


}
