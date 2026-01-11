package com.franchise.api.infra.adapter.persistence.repository.impl;

import org.springframework.stereotype.Component;
import com.franchise.api.domain.model.Franchise;
import com.franchise.api.domain.repository.FranchiseRepository;
import com.franchise.api.infra.adapter.persistence.mapper.FranchiseMapper;
import com.franchise.api.infra.adapter.persistence.repository.SpringDataFranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranchiseRepositoryAdapter implements FranchiseRepository {
    private final SpringDataFranchiseRepository repository;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return repository.save(FranchiseMapper.toDocument(franchise)).map(FranchiseMapper::toDomain);
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return repository.findById(id).map(FranchiseMapper::toDomain);
    }

    @Override
    public Mono<Franchise> updateName(String id, String newName) {
        return repository.findById(id)
                .map(doc -> { doc.setName(newName); return doc; })
                .flatMap(repository::save)
                .map(FranchiseMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByName(String name) { return repository.existsByName(name); }
}
