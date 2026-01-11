package com.franchise.api.infra.adapter.persistence.mapper;

import com.franchise.api.domain.model.Product;
import com.franchise.api.infra.adapter.persistence.documents.ProductDocument;

public class ProductMapper {
    public static Product toDomain(ProductDocument doc) {
        return Product.builder()
                .id(doc.getId()).name(doc.getName()).stock(doc.getStock()).branchId(doc.getBranchId())
                .createdAt(doc.getCreatedAt()).updatedAt(doc.getUpdatedAt()).build();
    }

    public static ProductDocument toDocument(Product domain) {
        return ProductDocument.builder()
                .id(domain.getId()).name(domain.getName()).stock(domain.getStock()).branchId(domain.getBranchId())
                .build();
    }
}
