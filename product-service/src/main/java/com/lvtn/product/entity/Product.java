package com.lvtn.product.entity;


import jakarta.persistence.*;
import lombok.*;

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
    private String brandName;
    private String categoryName;
    private Double price;
    private String imageUrl;
}
