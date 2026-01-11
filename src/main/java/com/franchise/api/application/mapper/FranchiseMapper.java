package com.franchise.api.application.mapper;

import com.franchise.api.application.dto.franchise.FranchiseRequestDTO;
import com.franchise.api.application.dto.franchise.FranchiseResponseDTO;
import com.franchise.api.domain.model.Franchise;

public class FranchiseMapper {
    public static Franchise toDomain(FranchiseRequestDTO dto) {
        return Franchise.builder()
                .name(dto.getName())
                .build();
    }

    public static FranchiseResponseDTO toResponseDTO(Franchise franchise) {
        return FranchiseResponseDTO.builder()
                .id(franchise.getId())
                .name(franchise.getName())
                .build();
    }
}
