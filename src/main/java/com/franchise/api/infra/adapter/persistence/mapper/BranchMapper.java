package com.franchise.api.infra.adapter.persistence.mapper;

import com.franchise.api.infra.adapter.persistence.entity.BranchEntity;
import com.franchise.api.domain.model.Branch;

public class BranchMapper {
    public static BranchEntity toEntity(Branch domain) {
        if (domain == null) return null;
        return BranchEntity.builder()
                .id(domain.getId())
                .franchiseId(domain.getFranchiseId())
                .name(domain.getName())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public static Branch toDomain(BranchEntity entity) {
        if (entity == null) return null;
        return Branch.builder()
                .id(entity.getId())
                .franchiseId(entity.getFranchiseId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
