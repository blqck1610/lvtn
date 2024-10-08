package com.lvtn.gateway.config;

import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FeignConfig
 * Version 1.0
 * Date: 01/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 01/10/2024        NGUYEN             create
 */
@Configuration
public class FeignConfig {
        @Bean
        public Decoder feignDecoder() {
            ObjectFactory<HttpMessageConverters> messageConverters = () -> {
                HttpMessageConverters converters = new HttpMessageConverters();
                return converters;
            };
            return new SpringDecoder(messageConverters);
        }

}
