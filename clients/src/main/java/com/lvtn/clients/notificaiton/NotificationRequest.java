package com.lvtn.clients.notificaiton;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    private String message;
    private String customerEmail;
    private Integer customerId;

}
