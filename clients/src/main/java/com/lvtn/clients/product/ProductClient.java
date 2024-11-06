package com.lvtn.clients.product;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "PRODUCT-SERVICE", path = "/api/v1/internal")
public interface ProductClient {
}
