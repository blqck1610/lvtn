package com.lvtn.user.controller;


import com.lvtn.clients.user.UserForAuth;
import com.lvtn.clients.user.UserRegistrationRequest;
import com.lvtn.user.dto.AddressDto;
import com.lvtn.user.dto.UserDto;
import com.lvtn.user.service.UserService;
import com.lvtn.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    @GetMapping(value = "/test")
//    public ResponseEntity<String> test(){
//        String t = userService.test();
//        return  ResponseEntity.ok(t);
//    }

    @PostMapping(value = "/create-new-user")
    public ResponseEntity<String>  register(@RequestBody @Valid UserRegistrationRequest request){
        ApiResponse apiResponse = userService.registerNewUser(request);
        log.info("created user {}", request);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse.getMessage());
    }
    @PostMapping(value = "/create-new-user/admin")
    public ResponseEntity<String>  registerAdmin(@RequestBody @Valid UserRegistrationRequest request){
        ApiResponse apiResponse = userService.registerAdmin(request);
        log.info("created user {}", request);
        return ResponseEntity.status(apiResponse.getCode()).body(apiResponse.getMessage());
    }
    @PostMapping(value = "/add-address")
    public ResponseEntity<String>  addAddress(@RequestHeader("username") String username,@RequestBody AddressDto addressDto){
        System.out.println(addressDto);
        System.out.println("1");

        return ResponseEntity.ok(userService.saveAddress(username, addressDto));
    }
    @GetMapping(value = "/get-address")
    public ResponseEntity<List<AddressDto>>  addAddress(@RequestHeader("username") String username){

        return ResponseEntity.ok(userService.getAddress(username));
    }



    @GetMapping(value = "/get-user/{userId}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable(value = "userId") Integer userId){
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }
    @GetMapping(value = "/find-by-username/{username}")
    public ResponseEntity<UserDto> findByUsername(@PathVariable(value = "username") String username){
        return ResponseEntity.ok(userService.findByUsername(username));
    }
    @GetMapping(value = "/get-user-info")
    public ResponseEntity<UserDto> getUserInfo(@RequestHeader(value = "username") String username){
        return ResponseEntity.ok(userService.findByUsername(username));
    }

//    @PutMapping(value = "/{userId}")
//    public ResponseEntity<UserDto> updateUserInfo(@PathVariable(value = "userId") Integer user){
////        todo: implement update user
//
//        return null;
//    }
//    @DeleteMapping(value = "/{userId}")
//    public ResponseEntity<UserDto> deleteUserInfo(@PathVariable(value = "userId") Integer user){
////        todo: implement delete user
//
//        return null;
//    }
    @GetMapping(value = "/auth")
    public ResponseEntity<UserForAuth> getUserForAuth(@RequestParam(value = "username") String username){

        return ResponseEntity.ok(userService.getUserForAuth(username));
    }

    @GetMapping(value = "/secured")
    public ResponseEntity<String> getSecured(){
        return ResponseEntity.ok("secured api");
    }

    @GetMapping(value = "/exists/username")
    public ResponseEntity<Boolean> isUserExists(@PathVariable(value = "username") String username){

        return ResponseEntity.ok(userService.isUserExists(username));
    }

    @GetMapping(value = "/test")
    public ResponseEntity<String> test(@RequestParam(value="test") String test){

        return ResponseEntity.ok("test");
    }

    @GetMapping(value = "/find-all")
    public ResponseEntity<List<UserDto>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }


}
