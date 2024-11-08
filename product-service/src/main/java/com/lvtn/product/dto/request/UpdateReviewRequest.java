package com.lvtn.product.dto.request;

import com.lvtn.product.dto.response.MediaDto;
import lombok.Data;

import java.util.List;

/**
 * UpdateReviewRequest
 * Version 1.0
 * Date: 08/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 08/11/2024        NGUYEN             create
 */
@Data
public class UpdateReviewRequest {
    private String id;
    private int rating;
    private String comment;
    private List<MediaDto> listMedia;
    private List<String> deleteMedia;
}
