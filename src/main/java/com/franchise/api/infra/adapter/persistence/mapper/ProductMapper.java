package com.franchise.api.infra.adapter.persistence.mapper;

import com.franchise.api.domain.model.Product;
import com.franchise.api.infra.adapter.persistence.entity.ProductEntity;

public class ProductMapper {
    public static ProductEntity toEntity(Product domain) {
        if (domain == null) return null;
        return ProductEntity.builder()
                .id(domain.getId())
                .branchId(domain.getBranchId())
                .name(domain.getName())
                .stock(domain.getStock())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public static Product toDomain(ProductEntity entity) {
        if (entity == null) return null;
        return Product.builder()
                .id(entity.getId())
                .branchId(entity.getBranchId())
                .name(entity.getName())
                .stock(entity.getStock())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
