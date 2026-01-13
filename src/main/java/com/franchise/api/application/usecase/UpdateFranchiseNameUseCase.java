package com.franchise.api.application.usecase;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import com.franchise.api.application.dto.franchise.FranchiseResponseDTO;
import com.franchise.api.application.mapper.FranchiseMapper;
import com.franchise.api.domain.exception.FranchiseNotFoundException;
import com.franchise.api.domain.model.Franchise;
import com.franchise.api.domain.repository.FranchiseRepository;

@Service
@RequiredArgsConstructor
public class UpdateFranchiseNameUseCase {
    private final FranchiseRepository franchiseRepository;

    public Mono<FranchiseResponseDTO> execute(Long franchiseId, String newName) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException("Franchise not found with id: " + franchiseId)))
                .map(product -> product.toBuilder().name(newName).build())
                .map(Franchise::validate)
                .flatMap(franchiseRepository::save)
                .map(FranchiseMapper::toResponseDTO);
    }
}
