package com.lvtn.order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLine {
    @Id
    @SequenceGenerator(name = "order_line_id_sequence",
            sequenceName = "order_line_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_line_id_sequence")
    private Integer id;

    @ManyToOne
    @JsonBackReference(value = "order_id")
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer productId;
    private String productName;
    private String brandName;
    private String description;
    private String categoryName;
    private Double price;
    private Integer quantity;
    private String imageUrl;

}
