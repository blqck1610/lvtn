package com.lvtn.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

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
                .route("auth-service", r ->
                        r.path("/api/v1/auth/**")
                        .uri("lb://AUTH-SERVICE")
                )
                .route("product-service", r -> r.path("/api/v1/product/**")
                        .uri("lb://PRODUCT-SERVICE")
                )
                .route("admin-service", r -> r.path("/api/v1/admin/**")
                        .uri("lb://ADMIN-SERVICE")
                )
                .route("order-service", r -> r.path("/api/v1/orders/**")
                        .uri("lb://ORDER-SERVICE")
                )
                .route("payment-service", r -> r.path("/api/v1/payment/**")
                        .uri("lb://PAYMENT-SERVICE")
                )
                .route("notification-service", r -> r.path("/api/v1/notifications/**")
                        .uri("lb://NOTIFICATION-SERVICE"))
                .build();
    }

}
