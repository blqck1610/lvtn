package com.lvtn.product.entity;
import com.lvtn.utils.common.Gender;
import com.lvtn.utils.constant.TableName;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = TableName.PRODUCT)
@EntityListeners(AuditingEntityListener.class)
public class Product extends BaseEntity {
    private String name;
    @ManyToOne()
    private Brand brand;
    @ManyToOne()
    private Category category;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String description;
    private String thumbnail;
}
