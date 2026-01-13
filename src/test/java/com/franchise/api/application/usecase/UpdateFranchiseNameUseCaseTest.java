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

import com.franchise.api.application.dto.franchise.FranchiseResponseDTO;
import com.franchise.api.domain.exception.FranchiseNotFoundException;
import com.franchise.api.domain.model.Franchise;
import com.franchise.api.domain.repository.FranchiseRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UpdateFranchiseNameUseCaseTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private UpdateFranchiseNameUseCase updateFranchiseNameUseCase;

    private Franchise existingFranchise;
    private Franchise updatedFranchise;

    @BeforeEach
    void setUp() {
        existingFranchise = Franchise.builder()
                .id(1L)
                .name("Old Franchise Name")
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now().minusDays(1))
                .build();

        updatedFranchise = Franchise.builder()
                .id(1L)
                .name("New Franchise Name")
                .createdAt(existingFranchise.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldUpdateFranchiseNameSuccessfully() {
        // Given
        Long franchiseId = 1L;
        String newName = "New Franchise Name";

        when(franchiseRepository.findById(franchiseId))
                .thenReturn(Mono.just(existingFranchise));
        when(franchiseRepository.save(any(Franchise.class)))
                .thenReturn(Mono.just(updatedFranchise));

        // When
        Mono<FranchiseResponseDTO> result = 
            updateFranchiseNameUseCase.execute(franchiseId, newName);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(1L, response.getId());
                    assertEquals("New Franchise Name", response.getName());
                    assertNotNull(response.getCreatedAt());
                    assertNotNull(response.getUpdatedAt());
                })
                .verifyComplete();

        verify(franchiseRepository).findById(franchiseId);
        verify(franchiseRepository).save(any(Franchise.class));
    }

    @Test
    void shouldThrowExceptionWhenFranchiseNotFound() {
        // Given
        Long nonExistentFranchiseId = 999L;
        String newName = "New Name";

        when(franchiseRepository.findById(nonExistentFranchiseId))
                .thenReturn(Mono.empty());

        // When
        Mono<FranchiseResponseDTO> result = 
            updateFranchiseNameUseCase.execute(nonExistentFranchiseId, newName);

        // Then
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> 
                    throwable instanceof FranchiseNotFoundException &&
                    throwable.getMessage().contains("Franchise not found with id: 999")
                )
                .verify();

        verify(franchiseRepository).findById(nonExistentFranchiseId);
        verify(franchiseRepository, never()).save(any(Franchise.class));
    }
}