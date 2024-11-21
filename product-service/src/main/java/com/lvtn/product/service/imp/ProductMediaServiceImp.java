package com.lvtn.product.service.imp;

import com.lvtn.product.dto.response.MediaDto;
import com.lvtn.product.entity.Product;
import com.lvtn.product.entity.ProductMedia;
import com.lvtn.product.repository.ProductMediaRepository;
import com.lvtn.product.service.ProductMediaService;
import com.lvtn.utils.common.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

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
@Slf4j
public class ProductMediaServiceImp implements ProductMediaService {
    private final ProductMediaRepository productMediaRepository;

    @Override
    @Transactional
    public void saveMedia(Product product, List<MediaDto> mediaList) {
        if(ObjectUtils.isEmpty(mediaList)){
            return;
        }
        for (MediaDto mediaDto : mediaList) {
            ProductMedia productMedia = ProductMedia.builder()
                    .product(product)
                    .mediaType(MediaType.valueOf(mediaDto.getMediaType()))
                    .resource(mediaDto.getResource())
                    .build();
            productMediaRepository.save(productMedia);
        }
        log.info("saved product media to repository");
    }

    @Override
    public List<MediaDto> getListProductMediaDto(Product product) {
        return List.of();
    }

    @Override
    @Transactional
    public void deleteMedia(UUID id, List<String> delMediaList) {

        for (String delMedia : delMediaList) {
            ProductMedia productMedia = productMediaRepository.getReferenceById(UUID.fromString(delMedia));
            productMediaRepository.delete(productMedia);
        }
    }

}
