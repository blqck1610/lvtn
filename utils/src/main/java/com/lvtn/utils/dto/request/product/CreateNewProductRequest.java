package com.lvtn.utils.dto.request.product;

import com.lvtn.utils.dto.response.product.MediaDto;
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
    private String gender;
    private List<MediaDto> mediaList;
    private Double price;
}
