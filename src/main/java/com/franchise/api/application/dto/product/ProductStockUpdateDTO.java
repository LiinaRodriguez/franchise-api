package com.franchise.api.application.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductStockUpdateDTO {
    private Integer stock;
}
