package com.franchise.api.infra.adapter.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.franchise.api.infra.adapter.persistence.entity.BranchEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BranchR2dbcRepository extends ReactiveCrudRepository<BranchEntity, Long> {

    Flux<BranchEntity> findByFranchiseId(Long franchiseId);
    Flux<BranchEntity> findAllByFranchiseId(Long franchiseId);
    
    Mono<Boolean> existsByIdAndFranchiseId(Long id, Long franchiseId);
}
