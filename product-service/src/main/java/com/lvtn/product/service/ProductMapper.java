package com.lvtn.product.service;

import com.lvtn.clients.product.ProductDto;
import com.lvtn.clients.product.PurchaseRequest;
import com.lvtn.clients.product.PurchaseResponse;
import com.lvtn.product.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .brandName(product.getBrand().getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .categoryName(product.getCategory().getName())
                .build();
    }
    PurchaseResponse toPurchaseResponse(Product product, Integer quantity) {
        return PurchaseResponse.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .brandName(product.getBrand().getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .categoryName(product.getCategory().getName())
                .quantity(quantity)
                .build();
    }

}
