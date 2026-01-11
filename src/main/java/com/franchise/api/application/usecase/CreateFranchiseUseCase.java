package com.franchise.api.application.usecase;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import com.franchise.api.application.mapper.FranchiseMapper;
import com.franchise.api.domain.repository.FranchiseRepository;
import com.franchise.api.domain.model.Franchise;
import com.franchise.api.application.dto.franchise.FranchiseRequestDTO;
import com.franchise.api.application.dto.franchise.FranchiseResponseDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateFranchiseUseCase {

    private final FranchiseRepository franchiseRepository;

    public Mono<FranchiseResponseDTO> execute(FranchiseRequestDTO requestDTO) {
        return Mono.just(FranchiseMapper.toDomain(requestDTO))
            .map(Franchise::validate)
            .flatMap(franchiseRepository::save)
            .map(FranchiseMapper::toResponseDTO);
    }
}
