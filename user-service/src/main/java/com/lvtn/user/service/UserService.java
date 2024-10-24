package com.lvtn.user.service;

import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.request.authenticate.AuthRequest;
import com.lvtn.utils.dto.request.user.UpdateUserRequest;
import com.lvtn.utils.dto.response.user.UserResponse;
import com.lvtn.utils.dto.request.authenticate.RegisterRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface UserService {

    @Transactional
    void delete();

    ApiResponse<UserResponse> authenticate(AuthRequest request);

    ApiResponse<UserResponse> register(RegisterRequest request);

    ApiResponse<UserResponse> getByUsername(String username);

    UserResponse getUserResponse();

    UserResponse update(UpdateUserRequest request);
}
