package com.lvtn.user.controller;



import com.lvtn.utils.dto.user.AddressDto;
import com.lvtn.utils.dto.user.UserDto;
import com.lvtn.user.service.UserService;

import com.lvtn.utils.dto.user.UserRegistrationRequest;
import com.lvtn.utils.dto.user.UserV0;
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



    @PostMapping(value = "/create-new-user")
    public UserV0 register(@RequestBody @Valid UserRegistrationRequest request){

        log.info("created user {}", request);
        return userService.registerNewUser(request);
    }

    @PostMapping(value = "/create-new-user/admin")
    public UserDto  registerAdmin(@RequestBody @Valid UserRegistrationRequest request){
        log.info("creating user {}", request);
        return userService.registerAdmin(request);
    }

    @PostMapping(value = "/add-address")
    public AddressDto addAddress(@RequestHeader("username") String username,@RequestBody AddressDto addressDto){
        System.out.println(addressDto);

        return userService.saveAddress(username, addressDto);
    }

    @GetMapping(value = "/get-address")
    public List<AddressDto>  addAddress(@RequestHeader("username") String username){
        return userService.getAddress(username);
    }



    @GetMapping(value = "/get-user/{userId}")
    public UserDto getUserInfo(@PathVariable(value = "userId") Integer userId){
        return userService.getUserInfo(userId);
    }
    @GetMapping(value = "/find-by-username/{username}")
    public UserDto findByUsername(@PathVariable(value = "username") String username){
        return userService.findByUsername(username);
    }
    @GetMapping(value = "/get-user-info")
    public UserDto getUserInfo(@RequestHeader(value = "username") String username){
        return userService.findByUsername(username);
    }


    @GetMapping(value = "/auth")
    public UserV0 getUserForAuth(@RequestParam(value = "username") String username){
        return userService.getUserForAuth(username);
    }

    @GetMapping(value = "/secured")
    public ResponseEntity<String> getSecured(){
        return ResponseEntity.ok("secured api");
    }



    @GetMapping(value = "/test")
    public ResponseEntity<String> test(@RequestParam(value="test") String test){

        return ResponseEntity.ok("test");
    }

    @GetMapping(value = "/find-all")
    public List<UserDto> findAll(){
        return userService.findAll();
    }




}
