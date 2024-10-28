package com.lvtn.product.entity;

import com.lvtn.utils.constant.TableName;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = TableName.BRAND)
public class Brand extends BaseEntity {
    private String name;
    private String description;
}
