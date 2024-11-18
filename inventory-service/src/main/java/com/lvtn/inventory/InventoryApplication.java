package com.lvtn.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * InventoryApplication
 * Version 1.0
 * Date: 06/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 06/11/2024        NGUYEN             create
 */
@SpringBootApplication(scanBasePackages = {
        "com.lvtn.inventory",
        "com.lvtn.utils",
        "com.lvtn.amqp"
})
@EnableFeignClients(basePackages = "com.lvtn.clients")
@EnableJpaAuditing
public class InventoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }
}
