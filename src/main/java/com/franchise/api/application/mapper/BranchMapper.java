package com.franchise.api.application.mapper;

import com.franchise.api.application.dto.branch.BranchRequestDTO;
import com.franchise.api.application.dto.branch.BranchResponseDTO;
import com.franchise.api.domain.model.Branch;
import static java.time.LocalDateTime.now;

public class BranchMapper {
    public static Branch toDomain(BranchRequestDTO dto, Long franchiseId) {
        return Branch.builder()
                .name(dto.getName())
                .franchiseId(franchiseId)
                .createdAt(now())
                .updatedAt(now())
                .build();
    }

    public static BranchResponseDTO toResponseDTO(Branch branch) {
        return BranchResponseDTO.builder()
                .id(branch.getId())
                .name(branch.getName())
                .franchiseId(branch.getFranchiseId())
                .createdAt(branch.getCreatedAt())
                .updatedAt(branch.getUpdatedAt())
                .build();
    }
}
