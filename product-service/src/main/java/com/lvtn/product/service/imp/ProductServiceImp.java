package com.lvtn.product.service.imp;

import com.lvtn.product.dto.request.CreateNewProductRequest;
import com.lvtn.product.dto.request.UpdateProductRequest;
import com.lvtn.product.dto.response.ProductResponse;
import com.lvtn.product.entity.Brand;
import com.lvtn.product.entity.Category;
import com.lvtn.product.entity.PriceHistory;
import com.lvtn.product.entity.Product;
import com.lvtn.product.filter.ProductFilter;
import com.lvtn.product.repository.PriceHistoryRepository;
import com.lvtn.product.repository.ProductRepository;
import com.lvtn.product.service.*;
import com.lvtn.utils.common.ErrorCode;
import com.lvtn.utils.dto.request.page.PagingRequest;
import com.lvtn.utils.exception.BaseException;
import com.lvtn.utils.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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
            Product product = buildProduct(request);
            priceHistoryRepository.save(PriceHistory.builder()
                    .product(product)
                    .price(request.getPrice())
                    .build());
            productMediaService.saveMedia(product, request.getMediaList());
            return mapper.from(product);
        } catch (Exception e) {
            log.error("Failed to create product");
            throw new BaseException(ErrorCode.BAD_REQUEST.getCode(), ErrorCode.BAD_REQUEST.getMessage());
        }
    }

    private Product buildProduct(CreateNewProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .brand(brandService.getBrandByName(request.getBrandName()))
                .category(categoryService.getCategoryByName(request.getCategoryName()))
                .thumbnail(request.getThumbnail())
                .gender(request.getGender())
                .build();
        product = productRepository.saveAndFlush(product);
        return product;
    }

    @Override
    public ProductResponse getProductDto(String id) {
        return mapper.from(getProduct(id));
    }

    @Override
    public ProductResponse updateProduct(UpdateProductRequest request) {
        Product product = getProduct(request.getId());
        if (ObjectUtils.isEmpty(product)) {
            log.error("not found product for id {}", request.getId());
            throw new BaseException(ErrorCode.BAD_REQUEST.getCode(), ErrorCode.BAD_REQUEST.getMessage());
        }
        product = updateProduct(request, product);
        return mapper.from(product);
    }

    @Override
    @Transactional
    public void deleteProduct(String id) {
        Product product = getProduct(id);
        product.setIsDelete(true);
    }

    @Override
    public Page<ProductResponse> getPageProduct(PagingRequest<ProductFilter> pagingRequest) {
        Pageable pageable = PageUtil.getPageable(pagingRequest);
        Page<Product> pageProduct = productRepository.getPageProduct(
                pageable,
                pagingRequest.getFilter().getKeyword(),
                pagingRequest.getFilter().getBrandName(),
                pagingRequest.getFilter().getCategoryName(),
                pagingRequest.getFilter().getPriceMin(),
                pagingRequest.getFilter().getPriceMax()
        )
        return null;
    }

    private Product updateProduct(UpdateProductRequest request, Product product) {
        Brand brand = brandService.getReferenceById(UUID.fromString(request.getBrand().getId()));
        Category category = categoryService.getReferenceById(UUID.fromString(request.getCategory().getId()));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setBrand(brand);
        product.setCategory(category);
        product.setGender(request.getGender());
        product.setThumbnail(request.getThumbnail());
        productMediaService.saveMedia(product, request.getAddMediaList());
        productMediaService.deleteMedia(product.getId(), request.getDelMediaList());
        product = productRepository.saveAndFlush(product);
        return product;
    }

    private Product getProduct(String id) {
        return productRepository.findProductById(UUID.fromString(id)).orElse(null);
    }


}
