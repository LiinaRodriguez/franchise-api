package com.franchise.api.infra.adapter.persistence.mapper;

import com.franchise.api.domain.model.Branch;
import com.franchise.api.infra.adapter.persistence.documents.BranchDocument;

public class BranchMapper {
public static Branch toDomain(BranchDocument doc) {
        return Branch.builder()
                .id(doc.getId()).name(doc.getName())
                .createdAt(doc.getCreatedAt()).updatedAt(doc.getUpdatedAt()).build();
    }

    public static BranchDocument toDocument(Branch domain) {
        return BranchDocument.builder()
                .id(domain.getId()).name(domain.getName())
                .build();
    }
}
