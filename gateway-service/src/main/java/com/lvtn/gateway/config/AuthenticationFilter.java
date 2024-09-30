package com.lvtn.gateway.config;

//import com.lvtn.clients.authentication.AuthenticationClient;
import com.lvtn.clients.authentication.AuthenticationClient;
import com.lvtn.gateway.service.JwtService;
import io.jsonwebtoken.Claims;
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

import java.util.Map;


@RefreshScope
@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter {

    private final RouterVallidator validator;
    private final JwtService jwtService;
    private final AuthenticationClient authenticationClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (validator.isSecured.test(request)) {
            if (authMissing(request)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
            String token = request.getHeaders().getOrEmpty("Authorization").getFirst();
//            token = token.substring(7);
//            System.out.println(token);
//            Map<String, Object> claims = authenticationClient.extractAllClaim(token);
//            System.out.println(claims);
//            todo: verify token
            if (jwtService.isExpired(token)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
//            todo: check token match db

            populateRequestWithHeaders(exchange,token);
//            System.out.println(request.getHeaders());
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
    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtService.extractAllClaims(token);
        exchange.getRequest().mutate()
                .header("username", String.valueOf(claims.getSubject()))
                .header("role", String.valueOf(claims.get("role")))
                .build();
    }
//    exctract service get without decode; use example: public string test(@RequestHeader String userId)
//    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
//        Claims claims = jwtService.extractAllClaims(token);
//        exchange.getRequest().mutate()
//                .header("id", String.valueOf(claims.get("id")))
//                .header("role", String.valueOf(claims.get("role")))
//                .build();
//    }
}
