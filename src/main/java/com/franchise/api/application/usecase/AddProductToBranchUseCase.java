package com.franchise.api.application.usecase;

import org.springframework.stereotype.Service;

import com.franchise.api.domain.repository.BranchRepository;
import com.franchise.api.domain.repository.ProductRepository;
import com.franchise.api.application.dto.product.ProductResponseDTO;
import com.franchise.api.application.dto.product.ProductRequestDTO;
import com.franchise.api.application.mapper.ProductMapper;
import com.franchise.api.domain.model.Product;
import com.franchise.api.domain.exception.BranchNotFoundException;
import reactor.core.publisher.Mono;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddProductToBranchUseCase {
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    public Mono<ProductResponseDTO> execute (ProductRequestDTO requestDTO, Long branchId){
        return branchRepository.findById(branchId)
            .switchIfEmpty(Mono.error(new BranchNotFoundException("Branch not found with id: " + branchId)))
            .flatMap(branch -> {
                Product product = ProductMapper.toDomain(requestDTO, branchId);
                return productRepository.save(product);
            })
            .map(ProductMapper::toResponseDTO);
    }
}
