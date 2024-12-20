package com.lvtn.order.entity;

import com.lvtn.utils.common.OrderStatus;
import com.lvtn.utils.common.PaymentMethod;
import com.lvtn.utils.constant.TableName;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

/**
 * Order
 * Version 1.0
 * Date: 08/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 08/11/2024        NGUYEN             create
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableName.ORDER)
@EntityListeners(AuditingEntityListener.class)
public class Order extends BaseEntity {
    private String username;
    private OrderStatus status;
    private String note;
    private Double totalAmount;
    private PaymentMethod paymentMethod;
}
