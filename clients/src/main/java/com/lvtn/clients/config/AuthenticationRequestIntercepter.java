package com.lvtn.clients.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * AuthenticationRequestIntercepter
 * Version 1.0
 * Date: 03/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 03/10/2024        NGUYEN             create
 */
// use bean for all clients
//    add atributes configuration for one client: such as :
//@FeignClient(value = "NOTIFICATION-SERVICE", path = "/api/v1/notification", configuration = {AuthenticationRequestIntercepter.class})
//public interface NotificationClient {

    @Component
public class AuthenticationRequestIntercepter implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        var token = requestAttributes.getRequest().getHeader("Authorization");
        if(StringUtils.hasText(token)){
            requestTemplate.header("Authorization", token);
        }
    }
}
