package com.lvtn.user.controller;



import com.lvtn.user.entity.User;
import com.lvtn.user.service.UserService;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.user.service.imp.UserServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final  UserService userService;

    @GetMapping(value = "/test")
    private ResponseEntity<ApiResponse<String>> test(){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.ACCEPTED, "ok ", "ok"));
    }

}
