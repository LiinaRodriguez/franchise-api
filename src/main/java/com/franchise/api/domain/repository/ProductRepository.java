package com.franchise.api.domain.repository;

import com.franchise.api.domain.model.Product;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> save(Product product);
    Mono<Void> deleteById(String id);
    Mono<Product> findById(String id);
    Mono<Product> updateStock(String id, Integer quantity);
    Mono<Product> updateName(String id, String newName);
    Mono<Product> findTopByBranchIdOrderByStockDesc(String branchId);
}
