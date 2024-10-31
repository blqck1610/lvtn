package com.lvtn.product.service;

import com.lvtn.product.dto.response.MediaDto;
import com.lvtn.product.entity.Product;

import java.util.List;
import java.util.UUID;

/**
 * ProductMediaService
 * Version 1.0
 * Date: 28/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 28/10/2024        NGUYEN             create
 */
public interface ProductMediaService {

    void saveMedia(Product product, List<MediaDto> mediaList);

    List<MediaDto> getListProductMediaDto(Product product);

    void deleteMedia(UUID id, List<String> delMediaList);
}
