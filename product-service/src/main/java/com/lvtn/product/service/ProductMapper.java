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
        PriceHistory priceHistory = priceHistoryRepository.getByProduct(product.getId()).orElse(null);
        Double price = ObjectUtils.isEmpty(priceHistory) ? null : priceHistory.getPrice();
        BrandResponse brandResponse = new BrandResponse(product.getBrand().getId().toString(), product.getBrand().getName());
        CategoryResponse categoryResponse = new CategoryResponse(product.getCategory().getId().toString(), product.getCategory().getName());
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .gender(product.getGender())
                .brand(brandResponse)
                .category(categoryResponse)
                .mediaDto(productMediaService.getListProductMediaDto(product))
                .price(price)
                .thumbnail(product.getThumbnail())
                .build();
    }
}
