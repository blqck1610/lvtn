package com.lvtn.order;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "com.lvtn.order",
        "com.lvtn.amqp",
        "com.lvtn.utils"
})
@EnableFeignClients(basePackages = "com.lvtn.clients")
public class OrderApplication {
}
