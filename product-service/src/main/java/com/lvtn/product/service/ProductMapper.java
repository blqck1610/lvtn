package com.lvtn.product.service;

import com.lvtn.utils.dto.product.ProductDto;
import com.lvtn.utils.dto.product.PurchaseResponse;
import com.lvtn.utils.dto.product.CartResponse;
import com.lvtn.utils.dto.product.ItemResponse;
import com.lvtn.product.entity.Cart;
import com.lvtn.product.entity.Item;
import com.lvtn.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .brandName(product.getBrand().getName())
                .price(product.getPrice())
                .availableQuantity(product.getAvailableQuantity())
                .gender(product.getGender().toString())
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
    ItemResponse toItemResponse(Item item){
        return  ItemResponse.builder()
                .product(toProductDto(item.getProduct()))
                .quantity(item.getQuantity())
                .build();
    }
    CartResponse toCartResponse(Cart cart) {
        return CartResponse.builder()
                .username(cart.getUsername())
                .items(cart.getItems().stream().map(this::toItemResponse).collect(Collectors.toList()))
                .build();

    }
}
