package com.lvtn.user.service;

import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.authenticate.AuthRequest;
import com.lvtn.utils.dto.user.UserDto;
import com.lvtn.utils.dto.user.UserRegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ApiResponse<UserDto> authenticate(AuthRequest request);

    ApiResponse<UserDto> registerNewUser(UserRegistrationRequest request);

    ApiResponse<UserDto> getByUsername(String username);
}
