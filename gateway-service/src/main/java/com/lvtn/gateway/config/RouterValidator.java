package com.lvtn.gateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.function.Predicate;

@Service
public class RouterValidator {

//use /.* instead of /*
    List<String> openEndpoints = List.of(
//            user
            "/api/v1/user/find-all",

//            authentication
            "/api/v1/auth/test",
            "/api/v1/auth/testa/.*",
            "/api/v1/auth/register",
            "/api/v1/auth/authenticate",
//order
            "/api/v1/orders/find-all",
//            product
            "/api/v1/product/product-details/.*",
            "/api/v1/product/search",
            "/api/v1/product/find-all",
            "/api/v1/product/add-product",
            "/api/v1/product/search/.*",
            "/api/v1/product/category/.*",
            "/api/v1/product/brand/.*",
            "/api/v1/product/new-arrivals",
            "/api/v1/product/gender/.*",
            "/api/v1/product/review/get-review-by-id/.*"

    );

    public Predicate<ServerHttpRequest> isSecured =
    request -> openEndpoints.stream().noneMatch(uri -> request.getURI().getPath().matches(uri));

}
