package com.lvtn.product.dto.request;

import com.lvtn.product.dto.response.BrandResponse;
import com.lvtn.product.dto.response.CategoryResponse;
import com.lvtn.utils.common.Gender;
import com.lvtn.product.dto.response.MediaDto;
import lombok.Data;

import java.util.List;

/**
 * UpdateProductRequest
 * Version 1.0
 * Date: 30/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 30/10/2024        NGUYEN             create
 */
@Data
public class UpdateProductRequest {
    private String id;
    private String name;
    private String description;
    private BrandResponse brand;
    private CategoryResponse category;
    private Gender gender;
    private String thumbnail;
    private List<MediaDto> AddMediaList;
    private List<String> delMediaList;
}
