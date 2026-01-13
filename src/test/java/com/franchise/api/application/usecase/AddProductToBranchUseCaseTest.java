package com.franchise.api.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.franchise.api.application.dto.product.ProductRequestDTO;
import com.franchise.api.application.dto.product.ProductResponseDTO;
import com.franchise.api.domain.exception.BranchNotFoundException;
import com.franchise.api.domain.model.Branch;
import com.franchise.api.domain.model.Product;
import com.franchise.api.domain.repository.BranchRepository;
import com.franchise.api.domain.repository.ProductRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class AddProductToBranchUseCaseTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private AddProductToBranchUseCase addProductToBranchUseCase;

    private Branch existingBranch;
    private ProductRequestDTO validRequestDTO;
    private Product savedProduct;

    @BeforeEach
    void setUp() {
        existingBranch = Branch.builder()
                .id(1L)
                .name("Test Branch")
                .franchiseId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        validRequestDTO = new ProductRequestDTO("Test Product", 50);

        savedProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .stock(50)
                .branchId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldAddProductToBranchSuccessfully() {
        Long branchId = 1L;

        when(branchRepository.findById(branchId))
                .thenReturn(Mono.just(existingBranch));
        when(productRepository.save(any(Product.class)))
                .thenReturn(Mono.just(savedProduct));

        Mono<ProductResponseDTO> result = 
            addProductToBranchUseCase.execute(validRequestDTO, branchId);

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(1L, response.getId());
                    assertEquals("Test Product", response.getName());
                    assertEquals(50, response.getStock());
                    assertEquals(1L, response.getBranchId());
                })
                .verifyComplete();

        verify(branchRepository).findById(branchId);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenBranchNotFound() {
        Long nonExistentBranchId = 999L;

        when(branchRepository.findById(nonExistentBranchId))
                .thenReturn(Mono.empty());

        Mono<ProductResponseDTO> result = 
            addProductToBranchUseCase.execute(validRequestDTO, nonExistentBranchId);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> 
                    throwable instanceof BranchNotFoundException &&
                    throwable.getMessage().contains("Branch not found with id: 999")
                )
                .verify();

        verify(branchRepository).findById(nonExistentBranchId);
        verify(productRepository, never()).save(any(Product.class));
    }

}