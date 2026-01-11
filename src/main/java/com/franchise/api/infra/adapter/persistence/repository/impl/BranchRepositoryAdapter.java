package com.franchise.api.infra.adapter.persistence.repository.impl;

import org.springframework.stereotype.Component;
import com.franchise.api.domain.model.Branch;
import com.franchise.api.domain.repository.BranchRepository;
import com.franchise.api.infra.adapter.persistence.mapper.BranchMapper;
import com.franchise.api.infra.adapter.persistence.repository.SpringDataBranchRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BranchRepositoryAdapter implements BranchRepository {
    private final SpringDataBranchRepository repository;

    @Override
    public Mono<Branch> save(Branch branch) {
        return repository.save(BranchMapper.toDocument(branch)).map(BranchMapper::toDomain);
    }

    @Override
    public Mono<Branch> findById(String id) {
        return repository.findById(id).map(BranchMapper::toDomain);
    }

    @Override
    public Flux<Branch> findAllByFranchiseId(String franchiseId) {
        return repository.findAllByFranchiseId(franchiseId).map(BranchMapper::toDomain);
    }

    @Override
    public Mono<Branch> updateName(String id, String newName) {
        return repository.findById(id)
                .map(doc -> { doc.setName(newName); return doc; })
                .flatMap(repository::save)
                .map(BranchMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByIdAndFranchiseId(String branchId, String franchiseId) {
        return repository.existsByIdAndFranchiseId(branchId, franchiseId);
    }
}