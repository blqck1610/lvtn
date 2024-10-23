package com.lvtn.clients.user;


import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.request.authenticate.AuthRequest;
import com.lvtn.utils.dto.response.user.UserResponse;
import com.lvtn.utils.dto.request.authenticate.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "USER-SERVICE", path = "/api/v1/internal/user")
public interface UserClient {

    @PostMapping
    public ApiResponse<UserResponse> register(@RequestBody @Valid RegisterRequest request);

    @PostMapping(value = "/authenticate")
    public ApiResponse<UserResponse> authenticate(@RequestBody AuthRequest request);

    @GetMapping(value = "/get-by-username")
    ApiResponse<UserResponse> getByUsername(@RequestParam String username);
}
