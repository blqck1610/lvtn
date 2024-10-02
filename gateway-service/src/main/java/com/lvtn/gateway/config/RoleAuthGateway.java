package com.lvtn.gateway.config;

import com.lvtn.clients.authentication.AuthenticationClient;
import com.lvtn.utils.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class RoleAuthGateway implements GatewayFilter {
    @Autowired
    private RouterVallidator validator;
    @Autowired
    private  AuthenticationClient authenticationClient;


    private String role;

    public RoleAuthGateway(String role) {
        this.role = role;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (validator.isSecured.test(request)) {
            if (authMissing(request)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
            final String token = request.getHeaders().getOrEmpty("Authorization").getFirst();
//
//            token = token.substring(7);
//            System.out.println(token);


            if(!authenticationClient.isTokenValid(token)){
                throw new BaseException(HttpStatus.UNAUTHORIZED, "Invalid token");
            }

            Map<String, Object> claims = authenticationClient.extractAllClaims(token);
            if (authenticationClient.isTokenExpired(token) ) {
//                return onError(exchange, HttpStatus.UNAUTHORIZED);
                throw new BaseException(HttpStatus.UNAUTHORIZED, "expired token");
            }
            if(!claims.get("role").equals(role)){
                throw new BaseException(HttpStatus.UNAUTHORIZED, "unauthorized");
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
