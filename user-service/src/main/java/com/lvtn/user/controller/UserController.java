package com.lvtn.user.controller;



import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.authenticate.AuthRequest;
import com.lvtn.utils.dto.user.UserDto;
import com.lvtn.user.service.UserService;
import com.lvtn.utils.dto.user.UserRegistrationRequest;
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
    public ApiResponse<UserDto> register(@RequestBody @Valid UserRegistrationRequest request){
        log.info("created user {}", request);
        return userService.registerNewUser(request);
    }


    @GetMapping(value = "/get-user/{userId}")
    public UserDto getUserInfo(@PathVariable(value = "userId") Integer userId){
        return userService.getUserInfo(userId);
    }
    @GetMapping(value = "/get-by-username")
    public ApiResponse<UserDto> findByUsername(@RequestParam(value = "username") String username){
        return userService.getByUsername(username);
    }

    @PostMapping(value = "/authenticate")
    public ApiResponse<UserDto> authenticate(@RequestBody AuthRequest request){
        return userService.authenticate(request);
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
