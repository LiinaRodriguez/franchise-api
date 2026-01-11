package com.franchise.api.infra.adapter.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import com.franchise.api.application.dto.product.ProductRequestDTO;
import com.franchise.api.application.dto.product.ProductResponseDTO;
import com.franchise.api.application.dto.product.ProductStockUpdateDTO;
import com.franchise.api.application.usecase.AddProductToBranchUseCase;
import com.franchise.api.application.usecase.DeleteProductUseCase;
import com.franchise.api.application.usecase.UpdateProductNameUseCase;
import com.franchise.api.application.usecase.UpdateProductStockUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(
    name = "Products", 
    description = "Product management API. Allows full CRUD operations for products associated with branches."
)
public class ProductController {

    private final AddProductToBranchUseCase addProductToBranchUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;
    private final UpdateProductNameUseCase updateProductNameUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    @PostMapping("/branches/{branchId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Add product to branch",
        description = "Creates a new product and associates it with an existing branch. " +
                      "The product includes a unique name and an initial stock quantity. " +
                      "Stock must be a non-negative number."
    )
    public Mono<ProductResponseDTO> addProductToBranch(
        @PathVariable String branchId,
        @RequestBody ProductRequestDTO requestDTO
    ) {
        return addProductToBranchUseCase.execute(requestDTO, branchId);
    }

    
    @PutMapping("/products/{productId}/stock")
    @Operation(
        summary = "Update product stock",
        description = "Modifies the available stock quantity of an existing product. " +
                      "The new stock must be a non-negative number. " +
                      "This operation completely replaces the previous value."
    )
   
    public Mono<ProductResponseDTO> updateProductStock(
        @PathVariable String productId,
        @RequestBody ProductStockUpdateDTO stockUpdateDTO
    ) {
        return updateProductStockUseCase.execute(productId, stockUpdateDTO);
    }

    
    @PutMapping("/products/{productId}/name")
    @Operation(
        summary = "Update product name",
        description = "Modifies the name of an existing product identified by its ID. " +
                      "The new name must meet the same validations as during creation and " +
                      "be unique within the branch."
    )
   
    public Mono<ProductResponseDTO> updateProductName(
        @PathVariable String productId,

        @RequestBody ProductRequestDTO requestDTO
    ) {
        return updateProductNameUseCase.execute(productId, requestDTO.getName());
    }

   
    @DeleteMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete product",
        description = "Permanently deletes a product from the system. " +
                      "This operation cannot be undone. " +
                      "The product will no longer be available in the associated branch."
    )
  
    public Mono<Void> deleteProduct(
        @PathVariable String productId
    ) {
        return deleteProductUseCase.execute(productId);
    }
}