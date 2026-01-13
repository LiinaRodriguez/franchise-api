package com.franchise.api.infra.adapter.persistence;

import com.franchise.api.domain.model.Product;
import com.franchise.api.domain.repository.ProductRepository;
import com.franchise.api.infra.adapter.persistence.mapper.ProductMapper;
import com.franchise.api.infra.adapter.persistence.repository.ProductR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductRepository {

    private final ProductR2dbcRepository r2dbcRepository;

    @Override
    public Mono<Product> save(Product product) {
        return r2dbcRepository.save(ProductMapper.toEntity(product))
                .map(ProductMapper::toDomain);
    }

    @Override
    public Mono<Product> findById(Long id) {
        return r2dbcRepository.findById(id)
                .map(ProductMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return r2dbcRepository.deleteById(id);
    }

   
    @Override
    public Flux<Product> findAllByBranchId(Long branchId) {
        return r2dbcRepository.findAllByBranchId(branchId)
                .map(ProductMapper::toDomain);
    }

    @Override
    public Mono<Product> findTopByBranchIdOrderByStockDesc(Long branchId) {
        return r2dbcRepository.findFirstByBranchIdOrderByStockDesc(branchId)
                .map(ProductMapper::toDomain);
    }
}