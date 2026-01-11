package com.franchise.api.infra.adapter.persistence.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.franchise.api.infra.adapter.persistence.documents.BranchDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SpringDataBranchRepository extends ReactiveMongoRepository<BranchDocument, String> {
    Flux<BranchDocument> findAllByFranchiseId(String franchiseId);
    Mono<Boolean> existsByIdAndFranchiseId(String id, String franchiseId);
}
