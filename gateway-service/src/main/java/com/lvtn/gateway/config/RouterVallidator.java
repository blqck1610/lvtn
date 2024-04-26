package com.lvtn.gateway.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.function.Predicate;

@Service
public class RouterVallidator {

    List<String> openEnpoints = List.of(
            "/api/v1/user/test"
    );

//    check if the requested endpoint is secured or open to the world
    public Predicate<ServerHttpRequest> isSecured =
            request -> openEnpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));

}
