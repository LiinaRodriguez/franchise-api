package com.franchise.api.infra.adapter.persistence;

import org.springframework.stereotype.Component;
import com.franchise.api.domain.model.Franchise;
import com.franchise.api.domain.repository.FranchiseRepository;
import com.franchise.api.infra.adapter.persistence.mapper.FranchiseMapper;
import com.franchise.api.infra.adapter.persistence.repository.FranchiseR2dbcRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class FranchisePersistenceAdapter implements FranchiseRepository {

    private final FranchiseR2dbcRepository r2dbcRepository;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return r2dbcRepository.save(FranchiseMapper.toEntity(franchise))
                .map(FranchiseMapper::toDomain);
    }

    @Override
    public Mono<Franchise> findById(Long id) {
        return r2dbcRepository.findById(id)
                .map(FranchiseMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return r2dbcRepository.existsByName(name);
    }

    @Override
    public Mono<Franchise> updateName(Long id, String name) {
        return r2dbcRepository.findById(id)
                .flatMap(entity -> {
                    entity.setName(name);
                    return r2dbcRepository.save(entity);
                })
                .map(FranchiseMapper::toDomain);
    }
}
