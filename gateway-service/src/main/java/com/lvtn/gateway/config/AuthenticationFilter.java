package com.lvtn.gateway.config;

import com.lvtn.gateway.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@RefreshScope
@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter {

    private final RouterVallidator validator;
    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (validator.isSecured.test(request)) {

            if (authMissing(request)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
            final String token = request.getHeaders().getOrEmpty("Authorization").getFirst();
//            todo: verify token
            if (jwtService.isExpired(token)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
        }

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}
