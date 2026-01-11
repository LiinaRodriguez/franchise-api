package com.franchise.api.application.usecase;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.franchise.api.application.dto.product.TopProductResponseDTO;
import com.franchise.api.application.mapper.ProductMapper;
import com.franchise.api.domain.repository.BranchRepository;
import com.franchise.api.domain.repository.ProductRepository;
import com.franchise.api.domain.repository.FranchiseRepository;
import com.franchise.api.domain.exception.FranchiseNotFoundException;



@Service
@RequiredArgsConstructor
public class GetTopStockProductsByFranchiseUseCase {

    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    private final FranchiseRepository franchiseRepository;

   public Flux<TopProductResponseDTO> execute(String franchiseId) {
    return franchiseRepository.findById(franchiseId)
            .switchIfEmpty(Mono.error(new FranchiseNotFoundException("Franchise with ID " + franchiseId + " not found")))
            .flatMapMany(franchise -> branchRepository.findAllByFranchiseId(franchise.getId()))
            .flatMap(branch -> 
                productRepository.findTopByBranchIdOrderByStockDesc(branch.getId())
                        .map(product -> ProductMapper.toTopStockDTO(product, branch.getName()))
            );
}
}