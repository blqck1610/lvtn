package com.lvtn.clients.notification;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "NOTIFICATION-SERVICE", path = "/api/v1/notification")
public interface NotificationClient {


}
