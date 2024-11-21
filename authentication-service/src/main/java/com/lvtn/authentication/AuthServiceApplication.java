package com.lvtn.authentication;

import com.lvtn.authentication.service.AuthService;
import com.lvtn.utils.dto.auth.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = {"com.lvtn.utils", "com.lvtn.authentication"})
@EnableFeignClients(basePackages = {"com.lvtn.clients"})
@EnableJpaAuditing
public class AuthServiceApplication implements CommandLineRunner {
    @Autowired
    private AuthService authService;

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        System.out.println("admin " + authService.getToken(new AuthRequest("admin", "password")).getAccessToken());
//        System.out.println("user " + authService.getToken(new AuthRequest("user", "password")).getAccessToken());
    }
}
