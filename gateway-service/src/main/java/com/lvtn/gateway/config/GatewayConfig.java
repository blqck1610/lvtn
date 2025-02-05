package com.lvtn.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
@RequiredArgsConstructor
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/v1/user/**")
                        .uri("lb://USER-SERVICE")
                )
                .route("auth-service", r -> r.path("/api/v1/auth/**")
                        .uri("lb://AUTH-SERVICE")
                )
                .route("product-service", r -> r.path("/api/v1/product/**")
                        .uri("lb://PRODUCT-SERVICE")
                )
                .route("cart-service", r -> r.path("/api/v1/cart/**")
                        .uri("lb://PRODUCT-SERVICE")
                )
                .route("payment-service", r -> r.path("/api/v1/payment/**")
                        .uri("lb://PAYMENT-SERVICE"))
                .build();
    }

}
