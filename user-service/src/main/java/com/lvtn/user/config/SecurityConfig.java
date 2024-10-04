package com.lvtn.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig
 * Version 1.0
 * Date: 03/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 03/10/2024        NGUYEN             create
 */
@Configuration
public class SecurityConfig {
    private static final String[] openEndpoints = {
            "/api/v1/user/create-new-user",
            ""
    };


    @Bean
    SecurityFilterChain filterChain(HttpSecurity https) throws  Exception{

        return  https.build();
    }

}
