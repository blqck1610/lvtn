package com.lvtn.product.entity;

import com.lvtn.utils.constant.TableName;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

/**
 * Item
 * Version 1.0
 * Date: 08/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 08/11/2024        NGUYEN             create
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = TableName.ITEM)
@EntityListeners(AuditingEntityListener.class)
public class Item extends  BaseEntity {
    @ManyToOne
    private Cart cart;
//    @ManyToOne
//    private Product product;
    private int quantity;
}
