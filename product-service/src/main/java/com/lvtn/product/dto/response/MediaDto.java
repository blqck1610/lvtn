package com.lvtn.product.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * MediaDto
 * Version 1.0
 * Date: 28/10/2024
 * Modification Logs
 * Copyright
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 28/10/2024        NGUYEN             create
 */
@Data
@Builder
public class MediaDto {
    private UUID id;
    private String resource;
    private String mediaType;
}
