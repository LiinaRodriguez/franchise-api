package com.franchise.api.application.usecase;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import com.franchise.api.application.dto.product.TopProductResponseDTO;
import com.franchise.api.application.mapper.ProductMapper;
import com.franchise.api.domain.repository.BranchRepository;
import com.franchise.api.domain.repository.ProductRepository;



@Service
@RequiredArgsConstructor
public class GetTopStockProductsByFranchiseUseCase {

    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    public Flux<TopProductResponseDTO> execute(String franchiseId) {
        // 1. Buscamos todas las sucursales de la franquicia
        return branchRepository.findAllByFranchiseId(franchiseId)
                .flatMap(branch -> 
                    // 2. Por cada sucursal, buscamos sus productos y tomamos el de mayor stock
                    productRepository.findTopByBranchIdOrderByStockDesc(branch.getId())
                            .map(product -> ProductMapper.toTopStockDTO(product, branch.getName()))
                );
    }
}