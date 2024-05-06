package com.lvtn.cart.entity;


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
    private int id;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private String imgSrc;
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "cart")
    @JoinColumn(name = "cart")
    private Cart cart;

    public double getTotalPrice() {
        return price * quantity;
    }
}
