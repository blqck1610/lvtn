package com.lvtn.user.service;

import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.auth.AuthRequest;
import com.lvtn.utils.dto.user.UpdatePasswordRequest;
import com.lvtn.utils.dto.user.UpdateUserRequest;
import com.lvtn.utils.dto.user.UserResponse;
import com.lvtn.utils.dto.auth.RegisterRequest;
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

    void changePassword(UpdatePasswordRequest request);

    void changeAvatar(String imageFile);
}
