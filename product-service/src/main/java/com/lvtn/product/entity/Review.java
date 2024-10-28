package com.lvtn.product.entity;

import com.lvtn.utils.constant.TableName;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = TableName.REVIEW)
public class Review extends BaseEntity {
    private UUID userId;
    private UUID productId;
    private int rating;
    private String comment;
}
