package com.lvtn.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Brand {
    @Id
    @SequenceGenerator(name = "brand_id_sequence",
            sequenceName = "brand_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_id_sequence")
    private  Integer id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "brand", cascade = CascadeType.REMOVE)
    private List<Product> products;
}
