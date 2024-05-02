package com.lvtn.clients.product;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "PRODUCT-SERVICE", path = "/api/v1/product")
public interface ProductClient {
    @PostMapping(value = "/test")
    public ResponseEntity<String> testSaveImg(@RequestBody MultipartFile file);

    @PostMapping(value = "/add-product")
    public ResponseEntity<String> addProduct(@RequestBody Product product, @RequestBody MultipartFile image);

    @GetMapping(value = "/test-callback")
    public ResponseEntity<String> testCall();
}
