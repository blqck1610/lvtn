package com.lvtn.order;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.lvtn.order",
        "com.lvtn.amqp",
        "com.lvtn.utils"
})
public class OrderApplication {
}
