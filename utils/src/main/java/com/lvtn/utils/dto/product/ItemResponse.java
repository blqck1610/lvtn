package com.lvtn.utils.dto.product;

import com.lvtn.clients.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {
    private ProductDto product;
    private Integer quantity;
}
