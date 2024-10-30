package com.lvtn.product.service.imp;

import com.lvtn.utils.common.MediaInfo;
import com.lvtn.utils.common.MediaType;
import com.lvtn.product.entity.Product;
import com.lvtn.product.entity.ProductMedia;
import com.lvtn.product.repository.ProductMediaRepository;
import com.lvtn.product.service.ProductMediaService;
import com.lvtn.utils.dto.response.product.MediaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ProductMediaServiceImp
 * Version 1.0
 * Date: 28/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 28/10/2024        NGUYEN             create
 */
@Service
@RequiredArgsConstructor
public class ProductMediaServiceImp implements ProductMediaService {
    private final ProductMediaRepository productMediaRepository;

    @Override
    @Transactional
    public void saveMedia(Product product, List<MediaDto> mediaList) {
        for (MediaDto mediaDto : mediaList) {
            ProductMedia productMedia = ProductMedia.builder()
                    .product(product)
                    .mediaInfo(MediaInfo.valueOf(mediaDto.getMediaInfo()))
                    .mediaType(MediaType.valueOf(mediaDto.getMediaType()))
                    .resource(mediaDto.getResource())
                    .build();
            productMediaRepository.save(productMedia);
        }
    }

    @Override
    public List<MediaDto> getListProductMediaDto(Product product) {
        return List.of();
    }

}
