package com.franchise.api.domain.repository;

import com.franchise.api.domain.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> save(Product product);
    Mono<Void> deleteById(String id);
    Mono<Product> findById(String id);
    Flux<Product> findAllByBranchId(String branchId);
    Mono<Product> findTopByBranchIdOrderByStockDesc(String branchId);
}
