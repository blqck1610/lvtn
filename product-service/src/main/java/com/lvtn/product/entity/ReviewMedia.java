package com.lvtn.product.entity;

import com.lvtn.utils.common.MediaInfo;
import com.lvtn.utils.common.MediaType;
import com.lvtn.utils.constant.TableName;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * ReviewMedia
 * Version 1.0
 * Date: 28/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 28/10/2024        NGUYEN             create
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = TableName.REVIEW_MEDIA)
@EntityListeners(AuditingEntityListener.class)
public class ReviewMedia extends BaseEntity {
    @ManyToOne
    private Review review;
    private String resource;
    private MediaType mediaType;
    private MediaInfo mediaInfo;
}
