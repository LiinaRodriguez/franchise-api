package com.franchise.api.application.mapper;

import com.franchise.api.application.dto.franchise.FranchiseRequestDTO;
import com.franchise.api.application.dto.franchise.FranchiseResponseDTO;
import com.franchise.api.domain.model.Franchise;
import static java.time.LocalDateTime.now;

public class FranchiseMapper {
    public static Franchise toDomain(FranchiseRequestDTO dto) {
        return Franchise.builder()
                .name(dto.getName())
                .createdAt(now())
                .updatedAt(now())
                .build();
    }

    public static FranchiseResponseDTO toResponseDTO(Franchise franchise) {
        return FranchiseResponseDTO.builder()
                .id(franchise.getId())
                .name(franchise.getName())
                .createdAt(franchise.getCreatedAt())
                .updatedAt(franchise.getUpdatedAt())
                .build();
    }
}
