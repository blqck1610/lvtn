package com.lvtn.product.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    @Id
    @SequenceGenerator(name = "product_id_sequence",
            sequenceName = "product_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_sequence")
    private  Integer id;
    private String productName;
    @ManyToOne()
    @JoinColumn(name = "brand_id")
    private Brand brand;
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
    private String description;
    private Integer availableQuantity;
    private Double price;
    private String imageUrl;
}
