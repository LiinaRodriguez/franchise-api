package com.franchise.api.infra.adapter.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.franchise.api.application.dto.branch.BranchRequestDTO;
import com.franchise.api.application.dto.branch.BranchResponseDTO;
import com.franchise.api.application.usecase.AddBranchToFranchiseUseCase;
import com.franchise.api.application.usecase.UpdateBranchNameUseCase;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor

public class BranchController {

    private final AddBranchToFranchiseUseCase addBranchToFranchiseUseCase;
    private final UpdateBranchNameUseCase updateBranchNameUseCase;

    @PostMapping("/franchises/{franchiseId}/branches")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BranchResponseDTO> addBranchToFranchise(
        @PathVariable Long franchiseId,
        @RequestBody BranchRequestDTO requestDTO
    ) {
        return addBranchToFranchiseUseCase.execute(requestDTO, franchiseId);
    }

    @PutMapping("/branches/{branchId}/name")
    public Mono<BranchResponseDTO> updateBranchName(
        @PathVariable Long branchId,
        @RequestBody BranchRequestDTO requestDTO
    ) {
        return updateBranchNameUseCase.execute(branchId, requestDTO.getName());
    }
}