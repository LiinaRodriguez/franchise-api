package com.franchise.api.application.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponseDTO {
    private String id;
    private String name;
    private Integer stock;
    private String branchId;
}
