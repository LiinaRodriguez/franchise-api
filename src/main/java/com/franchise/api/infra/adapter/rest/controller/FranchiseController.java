package com.franchise.api.infra.adapter.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import com.franchise.api.application.dto.franchise.FranchiseRequestDTO;
import com.franchise.api.application.dto.franchise.FranchiseResponseDTO;
import com.franchise.api.application.dto.product.TopProductResponseDTO;
import com.franchise.api.application.usecase.CreateFranchiseUseCase;
import com.franchise.api.application.usecase.UpdateFranchiseNameUseCase;
import com.franchise.api.application.usecase.GetTopStockProductsByFranchiseUseCase;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

/**
 * REST Controller for Franchise operations
 * 
 * Exposes reactive endpoints for franchise management
 * using reactive programming with Spring WebFlux.
 * 
 * @author Franchise API Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/franchises")
@RequiredArgsConstructor
@Tag(
    name = "Franchises", 
    description = "Franchise management API. Allows creating, updating, and querying franchise information."
)
public class FranchiseController {

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final UpdateFranchiseNameUseCase updateFranchiseNameUseCase;
    private final GetTopStockProductsByFranchiseUseCase getTopStockProductsByFranchiseUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Create new franchise",
        description = "Creates a new franchise in the system with the provided name. " +
                      "The name must be unique and cannot be empty."
    )
   
    public Mono<FranchiseResponseDTO> createFranchise(
        @RequestBody FranchiseRequestDTO requestDTO
    ) {
        return createFranchiseUseCase.execute(requestDTO);
    }

    @PutMapping("/{franchiseId}/name")
    @Operation(
        summary = "Update franchise name",
        description = "Updates the name of an existing franchise identified by its ID. " +
                      "The new name must meet the same validations as during creation."
    )
   
    public Mono<FranchiseResponseDTO> updateFranchiseName(
        @PathVariable String franchiseId,
        
        @RequestBody FranchiseRequestDTO requestDTO
    ) {
        return updateFranchiseNameUseCase.execute(franchiseId, requestDTO.getName());
    }

    @GetMapping("/{franchiseId}/top-stock-products")
    @Operation(
        summary = "Get products with highest stock per branch",
        description = "Returns a list of products that have the highest stock in each branch " +
                      "of the specified franchise. Useful for identifying the most available products " +
                      "at each location."
    )
    
    public Flux<TopProductResponseDTO> getTopStockProductsByFranchise(
        @PathVariable String franchiseId
    ) {
        return getTopStockProductsByFranchiseUseCase.execute(franchiseId);
    }
}