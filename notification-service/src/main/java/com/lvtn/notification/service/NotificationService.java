package com.lvtn.notification.service;


import com.lvtn.clients.notification.NotificationRequest;
import com.lvtn.notification.entity.Notification;
import com.lvtn.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void send(NotificationRequest request){

        Notification notification = Notification.builder()
                .sender("admin")
                .toCustomerEmail(request.getCustomerEmail())
                .toCustomerId(request.getCustomerId())
                .sendAt(LocalDateTime.now())
                .message(request.getMessage())
                .build();
        notificationRepository.save(notification);
        //      todo: send notification to user device

    }
}
