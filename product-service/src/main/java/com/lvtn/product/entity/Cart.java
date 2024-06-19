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
    private Integer id;
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
