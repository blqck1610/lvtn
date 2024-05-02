package com.lvtn.product.repository;

import com.lvtn.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT u FROM Product u WHERE u.categoryName = :categoryName")
    Page<Product> getProductByCategory(Pageable pageable,@Param("categoryName") String categoryName);

    @Query( """
                SELECT u
                FROM Product u
                WHERE (u.productName like CONCAT('%', :keyword,'%') OR :keyword IS NULL )
                AND (u.brandName in (:brands) OR :brands IS NULL)
                AND (u.categoryName in (:categories) OR :categories IS NULL)
   """)
    Page<Product> findProducts(Pageable pageable,@Param("keyword") String keyword, @Param("brands") List<String> brands,@Param("categories") List<String> categories);
}
