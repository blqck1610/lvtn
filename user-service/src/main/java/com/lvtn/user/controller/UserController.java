package com.lvtn.user.controller;


import com.lvtn.user.service.UserService;
import com.lvtn.utils.common.SuccessMessage;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.user.UpdatePasswordRequest;
import com.lvtn.utils.dto.user.UpdateUserRequest;
import com.lvtn.utils.dto.user.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.lvtn.utils.constant.ApiEndpoint.*;
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

    @PutMapping(value = PASSWORD)
    public ApiResponse<UserResponse> updatePassword(@RequestBody UpdatePasswordRequest request){
        userService.changePassword(request);
        return getApiResponse(HttpStatus.OK.value(),SuccessMessage.UPDATED_SUCCESS.getMessage(), null);
    }

    @PutMapping(value = AVATAR)
    public ApiResponse<UserResponse> changeAvatar(@RequestBody String image){
        userService.changeAvatar(image);
        return getApiResponse(HttpStatus.OK.value(),SuccessMessage.UPDATED_SUCCESS.getMessage(), null);
    }

}
