package com.franchise.api.infra.adapter.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import com.franchise.api.application.dto.branch.BranchRequestDTO;
import com.franchise.api.application.dto.branch.BranchResponseDTO;
import com.franchise.api.application.usecase.AddBranchToFranchiseUseCase;
import com.franchise.api.application.usecase.UpdateBranchNameUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(
    name = "Branches", 
    description = "Branch management API. Allows adding branches to franchises and updating their information."
)
public class BranchController {

    private final AddBranchToFranchiseUseCase addBranchToFranchiseUseCase;
    private final UpdateBranchNameUseCase updateBranchNameUseCase;

    
    @PostMapping("/franchises/{franchiseId}/branches")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Add branch to franchise",
        description = "Creates a new branch and associates it with an existing franchise. " +
                      "The branch will inherit the relationship with the specified franchise. " +
                      "The branch name must be unique within the franchise."
    )

    public Mono<BranchResponseDTO> addBranchToFranchise(
        @PathVariable String franchiseId,
        @RequestBody BranchRequestDTO requestDTO
    ) {
        return addBranchToFranchiseUseCase.execute(requestDTO, franchiseId);
    }

    @PutMapping("/branches/{branchId}/name")
    @Operation(
        summary = "Update branch name",
        description = "Updates the name of an existing branch identified by its ID. " +
                      "The new name must meet the same validations as during creation and " +
                      "be unique within the franchise."
    )

    public Mono<BranchResponseDTO> updateBranchName(
        @PathVariable String branchId,
        @RequestBody BranchRequestDTO requestDTO
    ) {
        return updateBranchNameUseCase.execute(branchId, requestDTO.getName());
    }
}