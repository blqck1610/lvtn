package com.lvtn.notification.service;


import com.lvtn.notification.email.EmailDetails;
import com.lvtn.notification.email.EmailService;
import com.lvtn.notification.entity.Notification;
import com.lvtn.notification.repository.NotificationRepository;
import com.lvtn.utils.common.Role;
import com.lvtn.utils.constant.Mail;
import com.lvtn.utils.dto.request.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    public void send(NotificationRequest request) {
        Notification notification = Notification.builder()
                .sender(Role.ADMIN.toString())
                .toCustomerEmail(request.getCustomerEmail())
                .notificationType(request.getType())
                .toCustomerId(request.getCustomerId())
                .message(request.getMessage())
                .isDelete(false)
                .build();
        notificationRepository.save(notification);
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(request.getCustomerEmail());
        emailDetails.setMsgBody(request.getMessage());
        emailDetails.setSubject(Mail.CREATED_CUSTOMER_SUBJECT);
        emailService.sendSimpleEmail(emailDetails);

    }
}
