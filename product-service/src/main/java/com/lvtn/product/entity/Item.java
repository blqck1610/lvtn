package com.lvtn.product.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
    @Id
    @SequenceGenerator(name = "item_id_sequence", sequenceName = "item_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_id_sequence")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "product")
    @JoinColumn(name = "product")
    private Product product;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "cart")
    @JoinColumn(name = "cart")
    private Cart cart;


}
