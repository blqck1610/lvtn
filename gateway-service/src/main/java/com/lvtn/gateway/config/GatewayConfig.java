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


    private final AuthenticationFilter filter;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/v1/user/**")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://USER-SERVICE")

                )
                .route("auth-service", r -> r.path("/api/v1/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://AUTH-SERVICE")
                )
                .route("product-service", r -> r.path("/api/v1/product/**")
                        .uri("lb://PRODUCT-SERVICE")
                )
                .route("admin-service", r -> r.path("/api/v1/admin/**")
                        .filters(f -> f.filter(new RoleAuthGateway("ADMIN")))
                        .uri("lb://ADMIN-SERVICE")
                )
                .route("cart-service", r -> r.path("/api/v1/cart/**")
//                        .filters(f -> f.filter(filter))
                        .uri("lb://CART-SERVICE")

                )
                .route("payment-service", r -> r.path("/api/v1/payment/**")
                        .uri("lb://PAYMENT-SERVICE")
                )

                .build();
    }

}
