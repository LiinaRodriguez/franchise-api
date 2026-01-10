package com.franchise.api.domain.repository;

import com.franchise.api.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {
    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> findById(String id);
    Mono<Franchise> updateName(String id, String newName);
    Mono<Boolean> existsByName(String name);
}
