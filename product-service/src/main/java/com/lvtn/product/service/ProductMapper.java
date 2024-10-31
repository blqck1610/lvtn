package com.lvtn.product.service;

import com.lvtn.product.dto.response.BrandResponse;
import com.lvtn.product.dto.response.CategoryResponse;
import com.lvtn.product.entity.PriceHistory;
import com.lvtn.product.entity.Product;
import com.lvtn.product.repository.PriceHistoryRepository;
import com.lvtn.product.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class ProductMapper {
    private final ProductMediaService productMediaService;
    private final PriceHistoryRepository priceHistoryRepository;

    public ProductResponse from(Product product) {
        if (ObjectUtils.isEmpty(product)) {
            return null;
        }
        PriceHistory priceHistory = priceHistoryRepository.getByProduct(product.getId());
        return ProductResponse.builder()
                .id(product.getId())
                .brand(new BrandResponse(product.getBrand().getId().toString(), product.getName()))
                .category(new CategoryResponse(product.getCategory().getId().toString(), product.getCategory().getName()))
                .mediaDto(productMediaService.getListProductMediaDto(product))
                .price(priceHistory.getPrice())
                .thumbnail(product.getThumbnail())
                .build();
    }
}
