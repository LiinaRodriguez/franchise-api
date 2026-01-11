package com.franchise.api.application.mapper;

import com.franchise.api.application.dto.product.ProductRequestDTO;
import com.franchise.api.application.dto.product.ProductResponseDTO;
import com.franchise.api.application.dto.product.TopProductResponseDTO;
import com.franchise.api.domain.model.Product;

public class ProductMapper {
    public static Product toDomain(ProductRequestDTO dto, String branchId) {
        return Product.builder()
                .name(dto.getName())
                .stock(dto.getStock())
                .branchId(branchId)
                .build();
    }

    public static ProductResponseDTO toResponseDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .stock(product.getStock())
                .branchId(product.getBranchId())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }


    public static TopProductResponseDTO toTopStockDTO(Product product, String branchName) {
        return TopProductResponseDTO.builder()
                .productName(product.getName())
                .branchName(branchName)
                .stock(product.getStock())
                .build();
    }
}
