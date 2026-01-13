package com.franchise.api.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.franchise.api.application.dto.product.ProductResponseDTO;
import com.franchise.api.domain.exception.ProductNotFoundException;
import com.franchise.api.domain.model.Product;
import com.franchise.api.domain.repository.ProductRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UpdateProductNameUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateProductNameUseCase updateProductNameUseCase;

    private Product existingProduct;
    private Product updatedProduct;

    @BeforeEach
    void setUp() {
        existingProduct = Product.builder()
                .id(1L)
                .name("Old Product Name")
                .stock(50)
                .branchId(1L)
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now().minusDays(1))
                .build();

        updatedProduct = Product.builder()
                .id(1L)
                .name("New Product Name")
                .stock(50)
                .branchId(1L)
                .createdAt(existingProduct.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldUpdateProductNameSuccessfully() {
        Long productId = 1L;
        String newName = "New Product Name";

        when(productRepository.findById(productId))
                .thenReturn(Mono.just(existingProduct));
        when(productRepository.save(any(Product.class)))
                .thenReturn(Mono.just(updatedProduct));

        Mono<ProductResponseDTO> result = 
            updateProductNameUseCase.execute(productId, newName);

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(1L, response.getId());
                    assertEquals("New Product Name", response.getName());
                    assertEquals(50, response.getStock());
                    assertEquals(1L, response.getBranchId());
                })
                .verifyComplete();

        verify(productRepository).findById(productId);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        Long nonExistentProductId = 999L;
        String newName = "New Name";

        when(productRepository.findById(nonExistentProductId))
                .thenReturn(Mono.empty());

        Mono<ProductResponseDTO> result = 
            updateProductNameUseCase.execute(nonExistentProductId, newName);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> 
                    throwable instanceof ProductNotFoundException &&
                    throwable.getMessage().contains("Product not found with id: 999")
                )
                .verify();

        verify(productRepository).findById(nonExistentProductId);
        verify(productRepository, never()).save(any(Product.class));
    }
}
