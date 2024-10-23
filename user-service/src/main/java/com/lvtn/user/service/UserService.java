package com.lvtn.user.service;

import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.request.authenticate.AuthRequest;
import com.lvtn.utils.dto.response.user.UserResponse;
import com.lvtn.utils.dto.request.authenticate.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ApiResponse<UserResponse> authenticate(AuthRequest request);

    ApiResponse<UserResponse> register(RegisterRequest request);

    ApiResponse<UserResponse> getByUsername(String username);

}
