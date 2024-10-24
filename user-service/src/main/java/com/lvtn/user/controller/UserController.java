package com.lvtn.user.controller;


import com.lvtn.user.service.UserService;
import com.lvtn.utils.common.SuccessMessage;
import com.lvtn.utils.constant.ApiEndpoint;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.request.user.UpdatePasswordRequest;
import com.lvtn.utils.dto.request.user.UpdateUserRequest;
import com.lvtn.utils.dto.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.lvtn.utils.constant.ApiEndpoint.BASE_API;
import static com.lvtn.utils.constant.ApiEndpoint.USER;
import static com.lvtn.utils.util.ResponseUtil.getApiResponse;

@RestController
@RequestMapping(value = BASE_API + USER)
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ApiResponse<UserResponse> getUserResponse() {
        return getApiResponse(HttpStatus.OK.value(), SuccessMessage.OK.getMessage(), userService.getUserResponse());
    }

    @DeleteMapping
    public ApiResponse<UserResponse> deleteUser() {
        userService.delete();
        return getApiResponse(HttpStatus.OK.value(), SuccessMessage.DELETE_SUCCESS.getMessage(), null);
    }

    @PutMapping
    public ApiResponse<UserResponse> updateUser(@RequestBody UpdateUserRequest request){
        return getApiResponse(HttpStatus.OK.value(),SuccessMessage.UPDATED_SUCCESS.getMessage(),userService.update(request));
    }

    @PutMapping(value = ApiEndpoint.PASSWORD)
    public ApiResponse<UserResponse> updatePassword(@RequestBody UpdatePasswordRequest request){
        userService.changePassword(request);
        return getApiResponse(HttpStatus.OK.value(),SuccessMessage.UPDATED_SUCCESS.getMessage(), null);
    }

}
