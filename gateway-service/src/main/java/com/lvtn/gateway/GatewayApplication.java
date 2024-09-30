package com.lvtn.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.lvtn.clients"})
@SpringBootApplication(scanBasePackages = {"com.lvtn.gateway", "com.lvtn.utils"})
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);

    }
}
