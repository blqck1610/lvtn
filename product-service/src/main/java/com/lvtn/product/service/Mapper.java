package com.lvtn.product.service;

import com.lvtn.product.dto.response.*;
import com.lvtn.product.entity.PriceHistory;
import com.lvtn.product.entity.Product;
import com.lvtn.product.entity.Review;
import com.lvtn.product.entity.ReviewMedia;
import com.lvtn.product.repository.PriceHistoryRepository;
import com.lvtn.product.repository.ReviewMediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Mapper {
    private final ProductMediaService productMediaService;
    private final PriceHistoryRepository priceHistoryRepository;
    private final ReviewMediaRepository reviewMediaRepository;


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

    public ReviewResponse from(Review review) {
        List<MediaDto> mediaList = reviewMediaRepository.findAllByProductId(review.getId())
                .stream().map(this::from).toList();
        return ReviewResponse.builder()
                .id(review.getId().toString())
                .username(review.getUsername())
                .comment(review.getComment())
                .listMedia(mediaList)
                .rating(review.getRating())
                .build();
    }

    public MediaDto from(ReviewMedia reviewMedia) {
        return MediaDto.builder()
                .id(reviewMedia.getId())
                .mediaInfo(String.valueOf(reviewMedia.getMediaInfo()))
                .mediaType(String.valueOf(reviewMedia.getMediaType()))
                .resource(reviewMedia.getResource())
                .build();
    }
}