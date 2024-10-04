package com.lvtn.gateway.config;

//import com.lvtn.clients.authentication.AuthenticationClient;

import com.lvtn.clients.authentication.AuthenticationClient;
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

    private final RouterValidator validator;
    private final AuthenticationClient authenticationClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (validator.isSecured.test(request)) {
            if (authMissing(request)) {
                return onError(exchange,"unauthenticated", HttpStatus.UNAUTHORIZED);
//                throw new BaseException(HttpStatus.UNAUTHORIZED, "token missing");
            }
            String token = request.getHeaders().getOrEmpty("Authorization").getFirst();
//            token = token.substring(7);
//            System.out.println(token);


            if (!authenticationClient.isTokenValid(token)) {
//                throw new BaseException(HttpStatus.UNAUTHORIZED, "Invalid token");
                return onError(exchange,"invalid token", HttpStatus.UNAUTHORIZED);
            }
//            todo: verify token
            if (authenticationClient.isTokenExpired(token)) {
                return onError(exchange,"expired token", HttpStatus.UNAUTHORIZED);
//                throw new BaseException(HttpStatus.UNAUTHORIZED, "Expired token");
            }

            populateRequestWithHeaders(exchange, token);
//            System.out.println(request.getHeaders());
        }

        return chain.filter(exchange).onErrorResume(throwable -> onError(exchange, throwable.toString(), HttpStatus.UNAUTHORIZED));
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(message.getBytes())));

    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Map<String, Object> claims = authenticationClient.extractAllClaims(token);
        exchange.getRequest().mutate()
                .header("username", String.valueOf(claims.get("username")))
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
