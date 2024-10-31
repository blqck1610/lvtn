package com.lvtn.product.dto.request;

import com.lvtn.product.dto.response.MediaDto;
import com.lvtn.utils.common.Gender;
import lombok.Data;

import java.util.List;

/**
 * CreateNewProductRequest
 * Version 1.0
 * Date: 28/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 28/10/2024        NGUYEN             create
 */
@Data
public class CreateNewProductRequest {
    private String name;
    private String description;
    private String brandName;
    private String categoryName;
    private Gender gender;
    private List<MediaDto> mediaList;
    private Double price;
    private String thumbnail;
}
