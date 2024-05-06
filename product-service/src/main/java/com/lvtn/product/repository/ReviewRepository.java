package com.lvtn.product.repository;

import com.lvtn.product.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT u FROM Review u WHERE  u.userId = :userId")
    Page<Review> getReviewsByUserId(Pageable pageable, @Param("userId") Integer userId);

    @Query("SELECT u FROM Review u WHERE  u.productId = :productId")
    Page<Review> getReviewByProduct(Pageable pageable,@Param("productId") Integer productId);

}
