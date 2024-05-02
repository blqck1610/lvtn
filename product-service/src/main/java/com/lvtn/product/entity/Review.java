package com.lvtn.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    @Id
    @SequenceGenerator(name = "review_id_sequence",
                        sequenceName = "review_id_sequence",
                allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_sequence")
    private Integer id;
    private String reviewerName;
    private Integer productId;
    private Integer rating;
    private String comment;
    private LocalDate reviewAt;


}
