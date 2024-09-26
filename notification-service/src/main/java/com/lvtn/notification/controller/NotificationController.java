package com.lvtn.notification.controller;



import com.lvtn.utils.dto.notification.NotificationRequest;
import com.lvtn.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;


    @PostMapping(value = "/send")
    public void sendNotification(@RequestBody NotificationRequest request){

        notificationService.send(request);

    }

    @GetMapping(value = "/test")
    public void test(){
        System.out.println("test");
        notificationService.test();
    }
}
