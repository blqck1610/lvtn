package com.lvtn.payment.paypal;

import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * PaypalConfig
 * Version 1.0
 * Date: 29/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 29/11/2024        NGUYEN             create
 */
@Configuration
public class PaypalConfig {
    @Value("${paypal.client-id}")
    private String clientId;
    @Value("${paypal.client-secret}")
    private String clientSecret;
    @Value("${paypal.mode}")
    private String mode;

    @Bean
    public APIContext apiContext(){
        return new APIContext(clientId, clientSecret, mode);
    }

    
}
