package com.lvtn.product.service;

import com.lvtn.product.entity.PriceHistory;
import com.lvtn.product.entity.Product;
import com.lvtn.product.repository.PriceHistoryRepository;
import com.lvtn.utils.dto.response.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductMapper {
    private final ProductMediaService productMediaService;
    private final PriceHistoryRepository priceHistoryRepository;

    public ProductResponse from(Product product) {
        PriceHistory priceHistory = priceHistoryRepository.getByProduct(product.getId());

        return ProductResponse.builder()
                .id(product.getId())
                .brand(product.getBrand().getName())
                .category(product.getCategory().getName())
                .mediaDto(productMediaService.getListProductMediaDto(product))
                .price(priceHistory.getPrice())
                .build();
    }
}
