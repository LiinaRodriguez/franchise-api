package com.franchise.api.infra.adapter.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.franchise.api.application.dto.franchise.FranchiseRequestDTO;
import com.franchise.api.application.dto.franchise.FranchiseResponseDTO;
import com.franchise.api.application.dto.product.TopProductResponseDTO;
import com.franchise.api.application.usecase.CreateFranchiseUseCase;
import com.franchise.api.application.usecase.GetTopStockProductsByFranchiseUseCase;
import com.franchise.api.application.usecase.UpdateFranchiseNameUseCase;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1/franchises")
@RequiredArgsConstructor

public class FranchiseController {

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final UpdateFranchiseNameUseCase updateFranchiseNameUseCase;
    private final GetTopStockProductsByFranchiseUseCase getTopStockProductsByFranchiseUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseResponseDTO> createFranchise(
        @RequestBody FranchiseRequestDTO requestDTO
    ) {
        return createFranchiseUseCase.execute(requestDTO);
    }

    @PutMapping("/{franchiseId}/name")   
    public Mono<FranchiseResponseDTO> updateFranchiseName(
        @PathVariable Long franchiseId,
        @RequestBody FranchiseRequestDTO requestDTO
    ) {
        return updateFranchiseNameUseCase.execute(franchiseId, requestDTO.getName());
    }

    @GetMapping("/{franchiseId}/top-stock-products")
    public Flux<TopProductResponseDTO> getTopStockProductsByFranchise(
        @PathVariable Long franchiseId
    ) {
        return getTopStockProductsByFranchiseUseCase.execute(franchiseId);
    }
}