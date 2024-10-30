package com.lvtn.product.service.imp;

import com.lvtn.utils.common.Gender;
import com.lvtn.product.entity.PriceHistory;
import com.lvtn.product.entity.Product;
import com.lvtn.product.repository.PriceHistoryRepository;
import com.lvtn.product.repository.ProductRepository;
import com.lvtn.product.service.*;
import com.lvtn.utils.common.ErrorCode;
import com.lvtn.utils.dto.request.product.CreateNewProductRequest;
import com.lvtn.utils.dto.response.product.ProductResponse;
import com.lvtn.utils.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ProductService
 * Version 1.0
 * Date: 28/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 28/10/2024        NGUYEN             create
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {
    private final ProductRepository productRepository;
    private final BrandService brandService;
    private final ProductMediaService productMediaService;
    private final CategoryService categoryService;
    private final PriceHistoryRepository priceHistoryRepository;
    private final ProductMapper mapper;


    @Override
    @Transactional
    public ProductResponse saveProduct(CreateNewProductRequest request) {
        try {
            Product product = Product.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .brand(brandService.getBrandByName(request.getBrandName()))
                    .category(categoryService.getCategoryByName(request.getCategoryName()))
                    .gender(Gender.valueOf(request.getGender().toUpperCase()))
                    .build();
            product = productRepository.saveAndFlush(product);
            priceHistoryRepository.save(PriceHistory.builder()
                    .product(product)
                    .price(request.getPrice())
                    .build());
            productMediaService.saveMedia(product, request.getMediaList());
            return mapper.from(product);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.BAD_REQUEST.getCode(), ErrorCode.BAD_REQUEST.getMessage());
        }
    }

}
