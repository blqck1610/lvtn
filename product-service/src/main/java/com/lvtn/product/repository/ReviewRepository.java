package com.lvtn.product.repository;

import com.lvtn.product.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("SELECT r FROM Review r WHERE  r.productId = :productId and r.isDelete != true")
    Page<Review> getReviewByProduct(Pageable pageable, @Param("productId") UUID productId);
}
