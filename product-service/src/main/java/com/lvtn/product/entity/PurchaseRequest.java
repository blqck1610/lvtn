package com.lvtn.product.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseRequest {
    @Id
    @SequenceGenerator(name = "purchaseRequest_id_sequence", sequenceName = "purchaseRequest_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseRequest_id_sequence")
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "product")
    @JoinColumn(name = "product")
    private Product product;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "cart")
    @JoinColumn(name = "cart")
    private Cart cart;

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}
