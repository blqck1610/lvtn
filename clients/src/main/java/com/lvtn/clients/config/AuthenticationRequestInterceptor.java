package com.lvtn.clients.config;

import com.lvtn.utils.constant.Attribute;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
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
//@FeignClient(value = "NOTIFICATION-SERVICE", path = "/api/v1/notification", configuration = {AuthenticationRequestInterceptor.class})
//public interface NotificationClient {

@Component
public class AuthenticationRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader(Attribute.AUTHORIZATION);
        if (!ObjectUtils.isEmpty(token)) {
            requestTemplate.header(Attribute.AUTHORIZATION, token);
        }
    }
}
//import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.context.annotation.Bean;
//
//@EnableFeignClients
//public class FeignConfig {
//
//    @Bean
//    public RequestInterceptor authenticationRequestInterceptor() {
//        return new AuthenticationRequestInterceptor();
//
