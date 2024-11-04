package com.lvtn.product.repository;

import com.lvtn.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("select p from Product p where p.id = :id and p.isDelete != true ")
    Optional<Product> findProductById(@Param("id") UUID id);

    @Query("""
            SELECT p
            FROM Product p LEFT JOIN (
                SELECT pr.price as price, pr.product.id as pid
                FROM PriceHistory pr
                WHERE pr.createdAt = (
                    SELECT max (pr2.createdAt)
                    FROM PriceHistory pr2
                    WHERE pr2.product.id = pr.product.id
                    )
                )  lastestPrice ON lastestPrice.pid = p.id
            WHERE (p.name like CONCAT('%',:keyword, '%') OR :keyword is null)
                AND (p.brand.name = :brandName OR :brandName is null)
                AND (p.category.name = :categoryName OR :categoryName is null)
                AND (lastestPrice.price > :min or :min is null)
                AND (lastestPrice.price < :max or :max is null)
            """)
    Page<Product> getPageProduct(Pageable pageable,
                                 @Param("keyword") String keyword,
                                 @Param("brandName") String brandName,
                                 @Param("categoryName") String categoryName,
                                 @Param("min") Double priceMin,
                                 @Param("max") Double priceMax);

    @Query("""
                    SELECT p.name
                    from Product p
                    WHERE p.isDelete != true AND p.name LIKE concat('%', :prefix,'%' )
                    ORDER BY p.createdAt DESC LIMIT 10
            """)
    List<String> getAutoComplete(@Param("prefix") String prefix);
}
