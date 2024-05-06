package com.lvtn.cart.controller;


import com.lvtn.cart.service.CartService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value ="/api/v1/cart")
@Slf4j
public class CartController {

    private final CartService cartService;

    @PostMapping(value ="/create")
    public void createCart(@RequestBody Integer userId){
        log.info("Create Cart {}", userId);
        cartService.createCart(userId);
    }
    @GetMapping(value = "/test")
    public ResponseEntity<String> test(){

        return ResponseEntity.ok("ok");
    }


}
