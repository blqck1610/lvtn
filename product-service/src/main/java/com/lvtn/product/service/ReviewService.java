package com.lvtn.product.service;

import com.lvtn.product.dto.request.CreateReviewRequest;
import com.lvtn.product.dto.request.UpdateReviewRequest;
import com.lvtn.product.dto.response.ReviewResponse;
import com.lvtn.product.filter.IdFilter;
import com.lvtn.utils.dto.request.page.PagingRequest;
import org.springframework.data.domain.Page;

public interface ReviewService {
    ReviewResponse createReview(CreateReviewRequest request);

    Page<ReviewResponse> getPageReview(PagingRequest<IdFilter> pagingRequest);

    ReviewResponse updateReview(UpdateReviewRequest request);

    void deleteReview(String id);
}
