package com.lvtn.product.repository;

import com.lvtn.product.entity.ReviewMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReviewMediaRepository extends JpaRepository<ReviewMedia, UUID> {

    @Query("select r from ReviewMedia r where r.review.id = :id and r.isDelete != true")
    List<ReviewMedia> findAllByProductId(@Param("id") UUID id);
}
