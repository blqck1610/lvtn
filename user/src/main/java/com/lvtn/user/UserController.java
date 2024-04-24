package com.lvtn.user;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/user")
@Slf4j
public class UserController {

    @GetMapping(value = "/test")
    public ResponseEntity<String> test(){
        log.info("user controller called");
        return  ResponseEntity.ok("ok");
    }

}
