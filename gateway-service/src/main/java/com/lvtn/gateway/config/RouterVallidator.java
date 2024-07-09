package com.lvtn.gateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.function.Predicate;

@Service
public class RouterVallidator {


    List<String> openEnpoints = List.of(
//            authentication
            "/api/v1/user/test",
            "/api/v1/auth/register",
            "/api/v1/auth/authenticate",
//            product
            "/api/v1/product/product-details/*",
            "/api/v1/product/find-all",
            "/api/v1/product/search/*",
            "/api/v1/product/category/*",
            "/api/v1/product/brand/*",
            "/api/v1/product/new-arrivals",
            "/api/v1/product/gender/*",
            "/api/v1/product/review/get-review-by-id/*"

    );

//    check if the requested endpoint is secured or open to the world
    public Predicate<ServerHttpRequest> isSecured =
            request -> openEnpoints.stream().noneMatch(uri -> request.getURI().getPath().matches(uri));

}
