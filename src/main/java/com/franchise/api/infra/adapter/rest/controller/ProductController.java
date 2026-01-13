package com.franchise.api.infra.adapter.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.franchise.api.application.dto.product.ProductRequestDTO;
import com.franchise.api.application.dto.product.ProductResponseDTO;
import com.franchise.api.application.dto.product.ProductStockUpdateDTO;
import com.franchise.api.application.usecase.AddProductToBranchUseCase;
import com.franchise.api.application.usecase.DeleteProductUseCase;
import com.franchise.api.application.usecase.UpdateProductNameUseCase;
import com.franchise.api.application.usecase.UpdateProductStockUseCase;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final AddProductToBranchUseCase addProductToBranchUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;
    private final UpdateProductNameUseCase updateProductNameUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    @PostMapping("/branches/{branchId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponseDTO> addProductToBranch(
        @PathVariable Long branchId,
        @RequestBody ProductRequestDTO requestDTO
    ) {
        return addProductToBranchUseCase.execute(requestDTO, branchId);
    }

    
    @PutMapping("/products/{productId}/stock")
    public Mono<ProductResponseDTO> updateProductStock(
        @PathVariable Long productId,
        @RequestBody ProductStockUpdateDTO stockUpdateDTO
    ) {
        return updateProductStockUseCase.execute(productId, stockUpdateDTO);
    }

    
    @PutMapping("/products/{productId}/name")
    public Mono<ProductResponseDTO> updateProductName(
        @PathVariable Long productId,
        @RequestBody ProductRequestDTO requestDTO
    ) {
        return updateProductNameUseCase.execute(productId, requestDTO.getName());
    }

   
    @DeleteMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(
        @PathVariable Long productId
    ) {
        return deleteProductUseCase.execute(productId);
    }
}