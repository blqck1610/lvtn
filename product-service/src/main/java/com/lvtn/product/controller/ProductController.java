package com.lvtn.product.controller;


import com.lvtn.product.entity.Product;
import com.lvtn.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    private ResponseEntity<String> addProduct(@RequestBody Product product, @RequestBody MultipartFile image){
        productService.addProduct(product, image);

        return null;
    }

}
