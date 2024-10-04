package com.lvtn.clients.user;


import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.authenticate.AuthRequest;
import com.lvtn.utils.dto.user.UserDto;
import com.lvtn.utils.dto.user.UserRegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "USER-SERVICE", path = "/api/v1/user")
public interface UserClient {

    @PostMapping(value = "/create-new-user")
    public ApiResponse<UserDto> register(@RequestBody @Valid UserRegistrationRequest request);

    @GetMapping(value = "/test")
    public ResponseEntity<String> test(@RequestParam(value = "test") String test);

    @GetMapping(value = "/get-by-username")
    public ApiResponse<UserDto> getByUsername(@RequestParam(value = "username") String username);

    @PostMapping(value = "/authenticate")
    public ApiResponse<UserDto> authenticate(@RequestBody AuthRequest request);

}
