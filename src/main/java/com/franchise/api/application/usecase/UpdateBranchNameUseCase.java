package com.franchise.api.application.usecase;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import com.franchise.api.application.dto.branch.BranchResponseDTO;  
import com.franchise.api.application.mapper.BranchMapper;
import com.franchise.api.domain.exception.BranchNotFoundException;
import com.franchise.api.domain.model.Branch;
import com.franchise.api.domain.repository.BranchRepository;


@Service
@AllArgsConstructor
public class UpdateBranchNameUseCase {
    private final BranchRepository branchRepository;

    public Mono<BranchResponseDTO> execute(String branchId, String newName) {
        return branchRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new BranchNotFoundException("Branch not found with id: " + branchId)))
                .map(branch -> branch.toBuilder().name(newName).build())
                .map(Branch::validate)
                .flatMap(branchRepository::save)
                .map(BranchMapper::toResponseDTO);
    }
}
