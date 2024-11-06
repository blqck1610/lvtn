package com.lvtn.product.repository;

import com.lvtn.product.entity.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, UUID> {
    @Query("""
             SELECT p
             FROM PriceHistory p
             WHERE p.product.id = :id AND p.isDelete != TRUE
             ORDER BY p.createdAt DESC LIMIT 1
            """)
    Optional<PriceHistory> getByProduct(@Param("id")UUID id);
}
