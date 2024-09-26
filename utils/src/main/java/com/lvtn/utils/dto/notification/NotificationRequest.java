package com.lvtn.utils.dto.notification;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {

    private String message;
    private String customerEmail;
    private Integer customerId;
    private NotificationType type;

}
