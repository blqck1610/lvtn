package com.lvtn.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = {"com.lvtn.notification", "com.lvtn.amqp", "com.lvtn.utils"})
@EnableFeignClients(basePackages = {"com.lvtn.clients"})
@EnableJpaAuditing
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

}
