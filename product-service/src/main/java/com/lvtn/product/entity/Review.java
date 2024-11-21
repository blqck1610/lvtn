package com.lvtn.product.entity;

import com.lvtn.utils.constant.TableName;
import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.realm.UserDatabaseRealm;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = TableName.REVIEW)
public class Review extends BaseEntity {
    private String username;
    @ManyToOne
    private Product product;
    private int rating;
    private String comment;
}
