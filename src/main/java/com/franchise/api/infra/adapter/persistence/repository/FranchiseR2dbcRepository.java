package com.franchise.api.infra.adapter.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.franchise.api.infra.adapter.persistence.entity.FranchiseEntity;

@Repository
public interface FranchiseR2dbcRepository extends ReactiveCrudRepository<FranchiseEntity, Long> {}
