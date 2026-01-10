package com.franchise.api.domain.repository;

import com.franchise.api.domain.model.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchRepository {
    Mono<Branch> save(Branch branch);
    Mono<Branch> findById(String id);
    Flux<Branch> findAllByFranchiseId(String franchiseId);
    Mono<Branch> updateName(String id, String newName);
    Mono<Boolean> existsByIdAndFranchiseId(String branchId, String franchiseId);
}
