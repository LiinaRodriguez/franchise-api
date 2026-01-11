package com.franchise.api.application.mapper;

import com.franchise.api.application.dto.branch.BranchRequestDTO;
import com.franchise.api.application.dto.branch.BranchResponseDTO;
import com.franchise.api.domain.model.Branch;

public class BranchMapper {
    public static Branch toDomain(BranchRequestDTO dto, String franchiseId) {
        return Branch.builder()
                .name(dto.getName())
                .franchiseId(franchiseId)
                .build();
    }

    public static BranchResponseDTO toResponseDTO(Branch branch) {
        return BranchResponseDTO.builder()
                .id(branch.getId())
                .name(branch.getName())
                .franchiseId(branch.getFranchiseId())
                .build();
    }
}
