package com.lvtn.product.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    @SequenceGenerator(name = "cart_id_sequence",
            sequenceName = "cart_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_id_sequence")
    private  Integer id;
    private String username;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "cart")
    @JsonBackReference(value = "cart")
    private List<Item> items;

    public Double getTotalPrice(){
        double rs = 0.0;
        for (Item item : items){
            rs += item.getProduct().getPrice() * item.getQuantity();
        }
        return rs;
    }


}
