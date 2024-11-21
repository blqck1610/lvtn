package com.lvtn.product.dto.request;

import com.lvtn.product.dto.response.MediaDto;
import com.lvtn.product.entity.ReviewMedia;
import lombok.Data;

import java.util.List;

/**
 * CreateReviewRequest
 * Version 1.0
 * Date: 08/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 08/11/2024        NGUYEN             create
 */
@Data
public class CreateReviewRequest {
    private String productId;
    private int rating;
    private String comment;
    private List<MediaDto> listMedia;
}
