package com.franchise.api.application.usecase;

import org.springframework.stereotype.Service;

import com.franchise.api.domain.exception.ProductNotFoundException;
import com.franchise.api.domain.repository.ProductRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DeleteProductUseCase {
    private final ProductRepository productRepository;

    public Mono<Void> execute(Long productId) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found with id: " + productId)))
                .flatMap(product -> productRepository.deleteById(product.getId()));
    }
}
