package com.lvtn.user.controller;


import com.lvtn.clients.user.UserDto;
import com.lvtn.clients.user.UserRegistrationRequest;
import com.lvtn.user.service.UserService;
import com.lvtn.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/test")
    public ResponseEntity<String> test(){
        String t = userService.test();
        return  ResponseEntity.ok(t);
    }

    @PostMapping(value = "/create-new-user")
    public ResponseEntity<String>  register(@RequestBody @Valid UserRegistrationRequest request){
        ApiResponse apiResponse = userService.registerNewUser(request);
        log.info("created user {}", request);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse.getMessage());
    }


    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable(value = "userId") Integer userId){
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<UserDto> updateUserInfo(@PathVariable(value = "userId") Integer user){
//        todo: implement update user

        return null;
    }
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<UserDto> deleteUserInfo(@PathVariable(value = "userId") Integer user){
//        todo: implement delete user

        return null;
    }

    @GetMapping(value = "/secured")
    public ResponseEntity<String> getSecured(){
        return ResponseEntity.ok("secured api");
    }




}
