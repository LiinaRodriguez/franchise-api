package com.franchise.api.application.usecase;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.franchise.api.domain.exception.ProductNotFoundException;
import com.franchise.api.domain.model.Product;
import com.franchise.api.domain.repository.ProductRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class DeleteProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProductUseCase deleteProductUseCase;

    private Product existingProduct;

    @BeforeEach
    void setUp() {
        existingProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .stock(50)
                .branchId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        Long productId = 1L;

        when(productRepository.findById(productId))
                .thenReturn(Mono.just(existingProduct));
        when(productRepository.deleteById(productId))
                .thenReturn(Mono.empty());

        Mono<Void> result = deleteProductUseCase.execute(productId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(productRepository).findById(productId);
        verify(productRepository).deleteById(productId);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        Long nonExistentProductId = 999L;

        when(productRepository.findById(nonExistentProductId))
                .thenReturn(Mono.empty());

        Mono<Void> result = deleteProductUseCase.execute(nonExistentProductId);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> 
                    throwable instanceof ProductNotFoundException &&
                    throwable.getMessage().contains("Product not found with id: 999")
                )
                .verify();

        verify(productRepository).findById(nonExistentProductId);
    }
}