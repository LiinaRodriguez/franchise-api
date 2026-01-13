package com.franchise.api.application.usecase;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import com.franchise.api.application.dto.product.ProductResponseDTO;
import com.franchise.api.application.mapper.ProductMapper;
import com.franchise.api.domain.exception.ProductNotFoundException;
import com.franchise.api.domain.model.Product;
import com.franchise.api.domain.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class UpdateProductNameUseCase {
    private final ProductRepository productRepository;

    public Mono<ProductResponseDTO> execute(Long productId, String newName) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found with id: " + productId)))
                .map(product -> product.toBuilder().name(newName).build())
                .map(Product::validate)
                .flatMap(productRepository::save)
                .map(ProductMapper::toResponseDTO);
    }
}