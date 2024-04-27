package com.lvtn.notification.controller;



import com.lvtn.clients.notificaiton.NotificationRequest;
import com.lvtn.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping(value = "/send")
    public void sendNotification(@RequestBody NotificationRequest request){

        notificationService.send(request);

    }
}
