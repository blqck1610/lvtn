package com.lvtn.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * OrderApplication
 * Version 1.0
 * Date: 08/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 08/11/2024        NGUYEN             create
 */
@SpringBootApplication(scanBasePackages = {
        "com.lvtn.order",
        "com.lvtn.amqp",
        "com.lvtn.utils"
})
@EnableFeignClients(basePackages = "com.lvtn.clients")
@EnableJpaAuditing
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
