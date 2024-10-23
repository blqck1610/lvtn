package com.lvtn.notification.service;


import com.lvtn.utils.dto.notification.NotificationRequest;
import com.lvtn.notification.email.EmailDetails;
import com.lvtn.notification.email.EmailService;
import com.lvtn.notification.entity.Notification;
import com.lvtn.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    public void send(NotificationRequest request){

        Notification notification = Notification.builder()
                .sender("admin")
                .toCustomerEmail(request.getCustomerEmail())
                .toCustomerId(request.getCustomerId())
                .sendAt(LocalDateTime.now())
                .message(request.getMessage())
                .build();
        notificationRepository.save(notification);
        //      todo: send notification to user email;
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(request.getCustomerEmail());
        emailDetails.setMsgBody("Customer created");
        emailDetails.setSubject("Customer created");
        emailService.sendSimpleEmail(emailDetails);

    }

    public void test(){
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient("tdnguyen16102002@gmail.com");
        emailDetails.setMsgBody("Customer created");
        emailDetails.setSubject("Customer created");
        System.out.println(emailService.sendSimpleEmail(emailDetails));

    }

}
