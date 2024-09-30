package com.lvtn.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.lvtn.notification", "com.lvtn.amqp", "com.lvtn.utils"})
@EnableFeignClients(basePackages = {"com.lvtn.clients"})
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

}
