package com.franchise.api.infra.adapter.persistence.repository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.franchise.api.infra.adapter.persistence.documents.ProductDocument;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SpringDataProductRepository extends ReactiveMongoRepository<ProductDocument, String> {
    Flux<ProductDocument> findAllByBranchId(String branchId);
    Mono<ProductDocument> findFirstByBranchIdOrderByStockDesc(String branchId);
}
