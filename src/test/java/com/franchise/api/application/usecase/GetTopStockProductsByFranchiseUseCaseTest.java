package com.franchise.api.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.franchise.api.application.dto.product.TopProductResponseDTO;
import com.franchise.api.domain.exception.FranchiseNotFoundException;
import com.franchise.api.domain.model.Branch;
import com.franchise.api.domain.model.Franchise;
import com.franchise.api.domain.model.Product;
import com.franchise.api.domain.repository.BranchRepository;
import com.franchise.api.domain.repository.FranchiseRepository;
import com.franchise.api.domain.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class GetTopStockProductsByFranchiseUseCaseTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetTopStockProductsByFranchiseUseCase getTopStockProductsByFranchiseUseCase;

    private Franchise existingFranchise;
    private Branch branch1;
    private Branch branch2;
    private Product product1;
    private Product product2;


    @BeforeEach
    void setUp() {
        existingFranchise = Franchise.builder()
                .id(1L)
                .name("Test Franchise")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        branch1 = Branch.builder()
                .id(1L)
                .name("Branch 1")
                .franchiseId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        branch2 = Branch.builder()
                .id(2L)
                .name("Branch 2")
                .franchiseId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        product1 = Product.builder()
                .id(1L)
                .name("Product 1")
                .stock(100)
                .branchId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        product2 = Product.builder()
                .id(2L)
                .name("Product 2")
                .stock(200)
                .branchId(2L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldGetTopStockProductsSuccessfully() {
        Long franchiseId = 1L;

        when(franchiseRepository.findById(franchiseId))
                .thenReturn(Mono.just(existingFranchise));
        when(branchRepository.findAllByFranchiseId(franchiseId))
                .thenReturn(Flux.just(branch1, branch2));
        when(productRepository.findTopByBranchIdOrderByStockDesc(1L))
                .thenReturn(Mono.just(product1));
        when(productRepository.findTopByBranchIdOrderByStockDesc(2L))
                .thenReturn(Mono.just(product2));

        Flux<TopProductResponseDTO> result = 
            getTopStockProductsByFranchiseUseCase.execute(franchiseId);

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals("Product 1", response.getProductName());
                    assertEquals(100, response.getStock());
                    assertEquals("Branch 1", response.getBranchName());
                })
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals("Product 2", response.getProductName());
                    assertEquals(200, response.getStock());
                    assertEquals("Branch 2", response.getBranchName());
                })
                .verifyComplete();

        verify(franchiseRepository).findById(franchiseId);
        verify(branchRepository).findAllByFranchiseId(franchiseId);
        verify(productRepository).findTopByBranchIdOrderByStockDesc(1L);
        verify(productRepository).findTopByBranchIdOrderByStockDesc(2L);
    }

    @Test
    void shouldReturnEmptyWhenFranchiseHasNoBranches() {
        Long franchiseId = 1L;

        when(franchiseRepository.findById(franchiseId))
                .thenReturn(Mono.just(existingFranchise));
        when(branchRepository.findAllByFranchiseId(franchiseId))
                .thenReturn(Flux.empty());

        Flux<TopProductResponseDTO> result = 
            getTopStockProductsByFranchiseUseCase.execute(franchiseId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(franchiseRepository).findById(franchiseId);
        verify(branchRepository).findAllByFranchiseId(franchiseId);
    }

    @Test
    void shouldReturnEmptyWhenBranchesHaveNoProducts() {
        Long franchiseId = 1L;

        when(franchiseRepository.findById(franchiseId))
                .thenReturn(Mono.just(existingFranchise));
        when(branchRepository.findAllByFranchiseId(franchiseId))
                .thenReturn(Flux.just(branch1));
        when(productRepository.findTopByBranchIdOrderByStockDesc(1L))
                .thenReturn(Mono.empty());

        Flux<TopProductResponseDTO> result = 
            getTopStockProductsByFranchiseUseCase.execute(franchiseId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(franchiseRepository).findById(franchiseId);
        verify(branchRepository).findAllByFranchiseId(franchiseId);
        verify(productRepository).findTopByBranchIdOrderByStockDesc(1L);
    }

    @Test
    void shouldReturnOneProductPerBranch() {
        Long franchiseId = 1L;

        when(franchiseRepository.findById(franchiseId))
                .thenReturn(Mono.just(existingFranchise));
        when(branchRepository.findAllByFranchiseId(franchiseId))
                .thenReturn(Flux.just(branch1));
        when(productRepository.findTopByBranchIdOrderByStockDesc(1L))
                .thenReturn(Mono.just(product1));

        Flux<TopProductResponseDTO> result = 
            getTopStockProductsByFranchiseUseCase.execute(franchiseId);

        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();

        verify(franchiseRepository).findById(franchiseId);
        verify(branchRepository).findAllByFranchiseId(franchiseId);
        verify(productRepository).findTopByBranchIdOrderByStockDesc(1L);
    }

    @Test
    void shouldThrowExceptionWhenFranchiseNotFound() {
        Long nonExistentFranchiseId = 999L;

        when(franchiseRepository.findById(nonExistentFranchiseId))
                .thenReturn(Mono.empty());

        Flux<TopProductResponseDTO> result = 
            getTopStockProductsByFranchiseUseCase.execute(nonExistentFranchiseId);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> 
                    throwable instanceof FranchiseNotFoundException &&
                    throwable.getMessage().contains("Franchise with ID 999 not found")
                )
                .verify();

        verify(franchiseRepository).findById(nonExistentFranchiseId);
    }
}