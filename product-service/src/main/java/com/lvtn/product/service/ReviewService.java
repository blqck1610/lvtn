package com.lvtn.product.service;


import com.lvtn.product.entity.Review;
import com.lvtn.product.repository.ReviewRepository;
import jdk.dynalink.linker.LinkerServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Review saveReview(Review review){
        return reviewRepository.save(review);
    }
    public void updateReview(Integer reviewId, Review review){
//        todo: implement update Review
    }
    public void deleteReview(Integer reviewId){
//        todo: implement delete Review
    }
    public Page<Review> getReviewsByUser(int page,Integer userId){
        Page<Review> rs = reviewRepository.getReviewsByUserId(getPageable(page, Sort.unsorted()),userId);
        return  rs;
    }
    public Page<Review> getReviewsByProduct(int page,Integer productId){
        Page<Review> rs = reviewRepository.getReviewByProduct(getPageable(page, Sort.unsorted()) ,productId);
        System.out.println(rs);
        return  rs;
    }

    private Pageable getPageable(int page, Sort sort) {
        int pageSize = 8;
        return PageRequest.of(page, pageSize, sort);
    }

}
