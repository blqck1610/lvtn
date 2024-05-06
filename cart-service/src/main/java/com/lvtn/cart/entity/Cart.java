package com.lvtn.cart.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    @SequenceGenerator(name = "cart_id_sequence",
            sequenceName = "cart_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_id_sequence")
    private Integer id;
    private Integer userId;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "cart")
    @JsonBackReference(value = "cart")
    private List<Item> items;

    public Double getTotalPrice(){
        return null;
    }


}
