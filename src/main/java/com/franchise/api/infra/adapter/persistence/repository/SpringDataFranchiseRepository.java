package com.franchise.api.infra.adapter.persistence.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.franchise.api.infra.adapter.persistence.documents.FranchiseDocument;

import reactor.core.publisher.Mono;

public interface SpringDataFranchiseRepository extends ReactiveMongoRepository<FranchiseDocument, String> {
    Mono<Boolean> existsByName(String name);
}
