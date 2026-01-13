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

import com.franchise.api.application.dto.branch.BranchRequestDTO;
import com.franchise.api.application.dto.branch.BranchResponseDTO;
import com.franchise.api.domain.exception.FranchiseNotFoundException;
import com.franchise.api.domain.model.Branch;
import com.franchise.api.domain.model.Franchise;
import com.franchise.api.domain.repository.BranchRepository;
import com.franchise.api.domain.repository.FranchiseRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class AddBranchToFranchiseUseCaseTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private AddBranchToFranchiseUseCase addBranchToFranchiseUseCase;

    private Franchise existingFranchise;
    private BranchRequestDTO validRequestDTO;
    private Branch savedBranch;

    @BeforeEach
    void setUp() {
        existingFranchise = Franchise.builder()
                .id(1L)
                .name("Test Franchise")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        validRequestDTO = new BranchRequestDTO("Test Branch");

        savedBranch = Branch.builder()
                .id(1L)
                .name("Test Branch")
                .franchiseId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldAddBranchToFranchiseSuccessfully() {
        Long franchiseId = 1L;

        when(franchiseRepository.findById(franchiseId))
                .thenReturn(Mono.just(existingFranchise));
        when(branchRepository.save(any(Branch.class)))
                .thenReturn(Mono.just(savedBranch));

        Mono<BranchResponseDTO> result = 
            addBranchToFranchiseUseCase.execute(validRequestDTO, franchiseId);

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(1L, response.getId());
                    assertEquals("Test Branch", response.getName());
                    assertEquals(1L, response.getFranchiseId());
                })
                .verifyComplete();

        verify(franchiseRepository).findById(franchiseId);
        verify(branchRepository).save(any(Branch.class));
    }

    @Test
    void shouldThrowExceptionWhenFranchiseNotFound() {
        Long nonExistentFranchiseId = 999L;

        when(franchiseRepository.findById(nonExistentFranchiseId))
                .thenReturn(Mono.empty());

        Mono<BranchResponseDTO> result = 
            addBranchToFranchiseUseCase.execute(validRequestDTO, nonExistentFranchiseId);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> 
                    throwable instanceof FranchiseNotFoundException &&
                    throwable.getMessage().contains("Franchise not found with id: 999")
                )
                .verify();

        verify(franchiseRepository).findById(nonExistentFranchiseId);
        verify(branchRepository, never()).save(any(Branch.class));
    }

}