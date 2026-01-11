package com.franchise.api.infra.adapter.persistence.repository.impl;

import org.springframework.stereotype.Component;
import com.franchise.api.domain.model.Product;
import com.franchise.api.domain.repository.ProductRepository;
import com.franchise.api.infra.adapter.persistence.mapper.ProductMapper;
import com.franchise.api.infra.adapter.persistence.repository.SpringDataProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
    private final SpringDataProductRepository repository;

    @Override
    public Mono<Product> save(Product product) {
        return repository.save(ProductMapper.toDocument(product)).map(ProductMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) { return repository.deleteById(id); }

    @Override
    public Mono<Product> findById(String id) {
        return repository.findById(id).map(ProductMapper::toDomain);
    }

    @Override
    public Flux<Product> findAllByBranchId(String branchId) {
        return repository.findAllByBranchId(branchId).map(ProductMapper::toDomain);
    }

    @Override
    public Mono<Product> findTopByBranchIdOrderByStockDesc(String branchId) {
        return repository.findFirstByBranchIdOrderByStockDesc(branchId).map(ProductMapper::toDomain);
    }
}