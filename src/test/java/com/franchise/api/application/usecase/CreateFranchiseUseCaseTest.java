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

import com.franchise.api.application.dto.franchise.FranchiseRequestDTO;
import com.franchise.api.application.dto.franchise.FranchiseResponseDTO;
import com.franchise.api.domain.exception.InvalidAttributeException;
import com.franchise.api.domain.model.Franchise;
import com.franchise.api.domain.repository.FranchiseRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CreateFranchiseUseCaseTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private CreateFranchiseUseCase createFranchiseUseCase;

    private FranchiseRequestDTO validRequestDTO;
    private Franchise savedFranchise;

    @BeforeEach
    void setUp() {
        validRequestDTO = new FranchiseRequestDTO("Test Franchise");
        
        savedFranchise = Franchise.builder()
                .id(1L)
                .name("Test Franchise")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldCreateFranchiseSuccessfully() {
        // Given
        when(franchiseRepository.save(any(Franchise.class)))
                .thenReturn(Mono.just(savedFranchise));

        // When
        Mono<FranchiseResponseDTO> result = 
            createFranchiseUseCase.execute(validRequestDTO);

        // Then
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(1L, response.getId());
                    assertEquals("Test Franchise", response.getName());
                    assertNotNull(response.getCreatedAt());
                    assertNotNull(response.getUpdatedAt());
                })
                .verifyComplete();

        verify(franchiseRepository).save(any(Franchise.class));
    }


    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        // Given
        FranchiseRequestDTO invalidRequest = new FranchiseRequestDTO("   ");

        // When
        Mono<FranchiseResponseDTO> result = 
            createFranchiseUseCase.execute(invalidRequest);

        // Then
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> 
                    throwable instanceof InvalidAttributeException &&
                    throwable.getMessage().contains("Name cannot be null or empty")
                )
                .verify();

        verify(franchiseRepository, never()).save(any(Franchise.class));
    }
}