package com.franchise.api.domain.repository;

import com.franchise.api.domain.model.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchRepository {
    Mono<Branch> save(Branch branch);
    Mono<Branch> findById(Long id);
    Flux<Branch> findAllByFranchiseId(Long franchiseId);
    Mono<Branch> updateName(Long id, String newName);
    Mono<Boolean> existsByIdAndFranchiseId(Long branchId, Long franchiseId);
}
