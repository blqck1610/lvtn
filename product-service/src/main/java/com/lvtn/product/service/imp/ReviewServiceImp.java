package com.lvtn.product.service.imp;

import com.lvtn.product.dto.request.CreateReviewRequest;
import com.lvtn.product.dto.request.UpdateReviewRequest;
import com.lvtn.product.dto.response.MediaDto;
import com.lvtn.product.dto.response.ReviewResponse;
import com.lvtn.product.entity.Product;
import com.lvtn.product.entity.Review;
import com.lvtn.product.entity.ReviewMedia;
import com.lvtn.product.filter.IdFilter;
import com.lvtn.product.repository.ReviewMediaRepository;
import com.lvtn.product.repository.ReviewRepository;
import com.lvtn.product.service.Mapper;
import com.lvtn.product.service.ProductService;
import com.lvtn.product.service.ReviewService;
import com.lvtn.utils.common.ErrorCode;
import com.lvtn.utils.common.MediaType;
import com.lvtn.utils.dto.request.page.PagingRequest;
import com.lvtn.utils.exception.BaseException;
import com.lvtn.utils.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.service.RequestBodyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

/**
 * ReviewServiceImo
 * Version 1.0
 * Date: 08/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 08/11/2024        NGUYEN             create
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImp implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final Mapper mapper;
    private final RequestBodyService requestBodyBuilder;
    private final ReviewMediaRepository reviewMediaRepository;

    @Override
    @Transactional
    public ReviewResponse createReview(CreateReviewRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            log.error("unauthenticated");
            throw new BaseException(ErrorCode.UNAUTHENTICATED.getCode(), ErrorCode.UNAUTHENTICATED.getMessage());
        }
        Product product = productService.getProduct(request.getProductId());
        if (ObjectUtils.isEmpty(product)) {
            log.error("product not found for id: {}", request.getProductId());
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), ErrorCode.BAD_REQUEST.getMessage());
        }
        Review review = Review.builder()
                .username(authentication.getName())
                .product(product)
                .rating(request.getRating())
                .comment(request.getComment())
                .build();
        review = reviewRepository.saveAndFlush(review);
        saveReviewMedia(request.getListMedia(), review);
        return mapper.from(review);
    }

    @Transactional
    void saveReviewMedia(List<MediaDto> listMedia, Review review) {
        if (ObjectUtils.isEmpty(listMedia)) {
            return;
        }
        for (MediaDto mediaDto : listMedia) {
            ReviewMedia reviewMedia = ReviewMedia.builder()
                    .review(review)
                    .mediaType(MediaType.valueOf(mediaDto.getMediaType()))
                    .resource(mediaDto.getResource())
                    .build();
            reviewMediaRepository.save(reviewMedia);
        }
    }


    @Override
    public Page<ReviewResponse> getPageReview(PagingRequest<IdFilter> pagingRequest) {
        Pageable pageable = PageUtil.getPageRequest(pagingRequest);
        Page<Review> reviewPage = reviewRepository.getReviewByProduct(pageable, UUID.fromString(pagingRequest.getFilter().getId()));
        return new PageImpl<>(reviewPage.stream().map(mapper::from).toList(), pageable, reviewPage.getTotalElements());
    }

    @Override
    @Transactional
    public ReviewResponse updateReview(UpdateReviewRequest request) {
        Review review = getReview(request.getId());
        if (ObjectUtils.isEmpty(review)) {
            log.error("review not found for id {}", request.getId());
            throw new BaseException(ErrorCode.BAD_REQUEST.getCode(), ErrorCode.BAD_REQUEST.getMessage());
        }
        review.setComment(request.getComment());
        review.setRating(request.getRating());
        saveReviewMedia(request.getListMedia(), review);
        deleteReviewMedia(request.getDeleteMedia());
        return mapper.from(review);
    }

    @Transactional
    void deleteReviewMedia(List<String> deleteMediaId) {
        for (String id : deleteMediaId) {
            ReviewMedia reviewMedia = reviewMediaRepository.getReferenceById(UUID.fromString(id));
            reviewMediaRepository.delete(reviewMedia);
        }
    }

    @Override
    @Transactional
    public void deleteReview(String id) {
        Review review = getReview(id);
        if(ObjectUtils.isEmpty(review)){
            log.error("review not found for id: {}", id);
            throw new BaseException(ErrorCode.BAD_REQUEST.getCode(), ErrorCode.BAD_REQUEST.getMessage());
        }
        reviewRepository.delete(review);
    }

    private Review getReview(String id) {
        return reviewRepository.getReviewById(UUID.fromString(id)).orElse(null);
    }
}
