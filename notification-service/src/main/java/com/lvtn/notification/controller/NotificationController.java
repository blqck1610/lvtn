package com.lvtn.notification.controller;



import com.lvtn.utils.constant.ApiEndpoint;
import com.lvtn.utils.dto.notification.NotificationRequest;
import com.lvtn.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ApiEndpoint.BASE_API + ApiEndpoint.NOTIFICATION)
@RequiredArgsConstructor
public class NotificationController {
}
