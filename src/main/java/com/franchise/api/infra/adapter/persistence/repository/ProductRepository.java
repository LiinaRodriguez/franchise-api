package com.franchise.api.infra.adapter.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.r2dbc.repository.Query;

import com.franchise.api.infra.adapter.persistence.entity.ProductEntity;

import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository  extends ReactiveCrudRepository<ProductEntity, Long> {

    Flux<ProductEntity> findByBranchId(Long branchId);

    @Query("SELECT p.* FROM products p " +
           "JOIN branches b ON p.branch_id = b.id " +
           "WHERE b.franchise_id = :franchiseId " +
           "AND p.stock = (" +
           "    SELECT MAX(p2.stock) " +
           "    FROM products p2 " +
           "    WHERE p2.branch_id = b.id" +
           ")")
    Flux<ProductEntity> findTopStockProductsByFranchise(Long franchiseId);
}
