package com.franchise.api.infra.adapter.persistence.mapper;

import com.franchise.api.domain.model.Franchise;
import com.franchise.api.infra.adapter.persistence.entity.FranchiseEntity;

public class FranchiseMapper {
    public static FranchiseEntity toEntity(Franchise domain) {
        if (domain == null) return null;
        return FranchiseEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public static Franchise toDomain(FranchiseEntity entity) {
        if (entity == null) return null;
        return Franchise.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
