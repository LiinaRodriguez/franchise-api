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

import com.franchise.api.application.dto.branch.BranchResponseDTO;
import com.franchise.api.domain.exception.BranchNotFoundException;
import com.franchise.api.domain.model.Branch;
import com.franchise.api.domain.repository.BranchRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UpdateBranchNameUseCaseTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private UpdateBranchNameUseCase updateBranchNameUseCase;

    private Branch existingBranch;
    private Branch updatedBranch;

    @BeforeEach
    void setUp() {
        existingBranch = Branch.builder()
                .id(1L)
                .name("Old Branch Name")
                .franchiseId(1L)
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now().minusDays(1))
                .build();

        updatedBranch = Branch.builder()
                .id(1L)
                .name("New Branch Name")
                .franchiseId(1L)
                .createdAt(existingBranch.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldUpdateBranchNameSuccessfully() {
        Long branchId = 1L;
        String newName = "New Branch Name";

        when(branchRepository.findById(branchId))
                .thenReturn(Mono.just(existingBranch));
        when(branchRepository.save(any(Branch.class)))
                .thenReturn(Mono.just(updatedBranch));

        Mono<BranchResponseDTO> result = 
            updateBranchNameUseCase.execute(branchId, newName);

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(1L, response.getId());
                    assertEquals("New Branch Name", response.getName());
                    assertEquals(1L, response.getFranchiseId());
                })
                .verifyComplete();

        verify(branchRepository).findById(branchId);
        verify(branchRepository).save(any(Branch.class));
    }

    @Test
    void shouldThrowExceptionWhenBranchNotFound() {
        Long nonExistentBranchId = 999L;
        String newName = "New Name";

        when(branchRepository.findById(nonExistentBranchId))
                .thenReturn(Mono.empty());

        Mono<BranchResponseDTO> result = 
            updateBranchNameUseCase.execute(nonExistentBranchId, newName);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> 
                    throwable instanceof BranchNotFoundException &&
                    throwable.getMessage().contains("Branch not found with id: 999")
                )
                .verify();

        verify(branchRepository).findById(nonExistentBranchId);
        verify(branchRepository, never()).save(any(Branch.class));
    }
}