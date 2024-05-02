package com.lvtn.product.service;


import com.lvtn.product.entity.Review;
import com.lvtn.product.repository.ReviewRepository;
import jdk.dynalink.linker.LinkerServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public void saveReview(Review review){
        reviewRepository.save(review);
    }
    public void updateReview(Integer reviewId, Review review){
//        todo: implement update Review
    }
    public void deleteReview(Integer reviewId){
//        todo: implement delete Review
    }
    public List<Review> getReviewsByUser(Integer userId){
        List<Review> rs = reviewRepository.getReviewsByUserId(userId);
        return  rs;
    }
    public List<Review> getReviewsByProduct(Integer productId){
        List<Review> rs = reviewRepository.getReviewByProduct(productId);
        return  rs;
    }

}
