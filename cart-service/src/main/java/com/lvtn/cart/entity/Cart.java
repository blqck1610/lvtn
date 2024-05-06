package com.lvtn.cart.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    private Integer id;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "cart")
    @JsonBackReference(value = "cart")
    private List<Item> items;

    public Double getTotalPrice(){
        double rs = 0.0;
        for (Item item : items){
            rs += item.getPrice() * item.getQuantity();
        }
        return rs;
    }


}
