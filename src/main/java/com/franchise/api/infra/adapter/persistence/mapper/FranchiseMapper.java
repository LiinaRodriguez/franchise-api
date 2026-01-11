package com.franchise.api.infra.adapter.persistence.mapper;

import com.franchise.api.domain.model.Franchise;
import com.franchise.api.infra.adapter.persistence.documents.FranchiseDocument;

public class FranchiseMapper {
        public static Franchise toDomain(FranchiseDocument doc) {
        return Franchise.builder()
                .id(doc.getId()).name(doc.getName())
                .createdAt(doc.getCreatedAt()).updatedAt(doc.getUpdatedAt()).build();
    }

    public static FranchiseDocument toDocument(Franchise domain) {
        return FranchiseDocument.builder()
                .id(domain.getId()).name(domain.getName())
                .build();
    }
}
