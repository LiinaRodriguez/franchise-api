package com.franchise.api.application.usecase;

import reactor.core.publisher.Mono;
import com.franchise.api.application.dto.branch.BranchRequestDTO;
import com.franchise.api.application.dto.branch.BranchResponseDTO;
import com.franchise.api.domain.repository.BranchRepository;
import com.franchise.api.domain.repository.FranchiseRepository;
import org.springframework.stereotype.Service;
import com.franchise.api.domain.model.Branch;
import com.franchise.api.application.mapper.BranchMapper;
import com.franchise.api.domain.exception.FranchiseNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddBranchToFranchiseUseCase {
    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;

    public Mono<BranchResponseDTO> execute(BranchRequestDTO requestDTO, Long franchiseId) {
        return franchiseRepository.findById(franchiseId)
            .switchIfEmpty(Mono.error(new FranchiseNotFoundException("Franchise not found with id: " + franchiseId)))
            .flatMap(franchise -> {
                Branch branch = BranchMapper.toDomain(requestDTO, franchiseId);
                return branchRepository.save(branch);
            })
            .map(BranchMapper::toResponseDTO);
        }
}
