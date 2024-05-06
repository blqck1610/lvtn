package com.lvtn.clients.cart;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "CART-SERVICE", path = "/api/v1/cart")
public interface CartClient {
    @PostMapping(value ="/create")
    public void createCart(@RequestBody Integer userId);
    @GetMapping(value = "/test")
    public ResponseEntity<String> test();

}
