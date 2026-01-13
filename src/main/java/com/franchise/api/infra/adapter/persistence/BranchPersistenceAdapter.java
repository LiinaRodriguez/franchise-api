package com.franchise.api.infra.adapter.persistence;

import com.franchise.api.domain.model.Branch;
import com.franchise.api.domain.repository.BranchRepository;
import com.franchise.api.infra.adapter.persistence.mapper.BranchMapper;
import com.franchise.api.infra.adapter.persistence.repository.BranchR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BranchPersistenceAdapter implements BranchRepository {

    private final BranchR2dbcRepository r2dbcRepository;

   @Override
    public Mono<Branch> save(Branch branch) {
        return r2dbcRepository.save(BranchMapper.toEntity(branch))
                .map(BranchMapper::toDomain);
    }

    @Override
    public Mono<Branch> findById(Long id) {
        return r2dbcRepository.findById(id)
                .map(BranchMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByIdAndFranchiseId(Long id, Long franchiseId) {
        return r2dbcRepository.existsByIdAndFranchiseId(id, franchiseId);
    }

    @Override
    public Flux<Branch> findAllByFranchiseId(Long franchiseId) {
        return r2dbcRepository.findAllByFranchiseId(franchiseId)
                .map(BranchMapper::toDomain);
    }

    @Override
    public Mono<Branch> updateName(Long id, String name) {
        return r2dbcRepository.findById(id)
                .flatMap(entity -> {
                    entity.setName(name);
                    return r2dbcRepository.save(entity);
                })
                .map(BranchMapper::toDomain);
    }
}