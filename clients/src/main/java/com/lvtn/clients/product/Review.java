package com.lvtn.clients.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

    private Integer id;
    private Integer userId;
    private String reviewerName;
    private Integer productId;

    private Integer rating;
    private String comment;
    private LocalDate reviewAt;


}
