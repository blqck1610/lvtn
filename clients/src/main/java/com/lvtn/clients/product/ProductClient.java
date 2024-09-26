package com.lvtn.clients.product;


import com.lvtn.utils.dto.product.PurchaseRequest;
import com.lvtn.utils.dto.product.PurchaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "PRODUCT-SERVICE", path = "/api/v1/product")
public interface ProductClient {


    @PostMapping(value = "/purchase")
    public ResponseEntity<List<PurchaseResponse>> purchaseProducts(@RequestBody List<PurchaseRequest> requests);

    @PostMapping(value = "/cart/clear-cart")
    public ResponseEntity<String> clearCart(@RequestBody String username);



}
