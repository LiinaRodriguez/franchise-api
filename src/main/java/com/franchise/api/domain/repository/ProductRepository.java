package com.franchise.api.domain.repository;

import com.franchise.api.domain.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> save(Product product);
    Mono<Void> deleteById(Long id);
    Mono<Product> findById(Long id);
    Flux<Product> findAllByBranchId(Long branchId);
    Mono<Product> findTopByBranchIdOrderByStockDesc(Long branchId);
}
