package com.franchise.api.infra.adapter.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.franchise.api.application.dto.branch.BranchRequestDTO;
import com.franchise.api.application.dto.branch.BranchResponseDTO;
import com.franchise.api.application.dto.franchise.FranchiseRequestDTO;
import com.franchise.api.application.dto.franchise.FranchiseResponseDTO;
import com.franchise.api.application.dto.product.*;
import com.franchise.api.application.usecase.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = {FranchiseController.class, BranchController.class, ProductController.class})
class ControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private CreateFranchiseUseCase createFranchiseUseCase;

    @MockitoBean
    private UpdateFranchiseNameUseCase updateFranchiseNameUseCase;

    @MockitoBean
    private GetTopStockProductsByFranchiseUseCase getTopStockProductsByFranchiseUseCase;

    @MockitoBean
    private AddBranchToFranchiseUseCase addBranchToFranchiseUseCase;

    @MockitoBean
    private UpdateBranchNameUseCase updateBranchNameUseCase;

    @MockitoBean
    private AddProductToBranchUseCase addProductToBranchUseCase;

    @MockitoBean
    private UpdateProductStockUseCase updateProductStockUseCase;

    @MockitoBean
    private UpdateProductNameUseCase updateProductNameUseCase;

    @MockitoBean
    private DeleteProductUseCase deleteProductUseCase;

    @Test
    void shouldCreateFranchise_Returns201() {
        FranchiseRequestDTO requestDTO = new FranchiseRequestDTO("Test Franchise");
        FranchiseResponseDTO responseDTO = new FranchiseResponseDTO(
            1L, 
            "Test Franchise", 
            LocalDateTime.now(), 
            LocalDateTime.now()
        );

        when(createFranchiseUseCase.execute(any(FranchiseRequestDTO.class)))
                .thenReturn(Mono.just(responseDTO));

        webTestClient.post()
                .uri("/api/v1/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Test Franchise");
    }

    @Test
    void shouldGetTopStockProducts_ReturnsFlux() {
        TopProductResponseDTO product1 = TopProductResponseDTO.builder()
            .productName("Product 1")
            .stock(100)
            .branchName("Branch 1")
            .build();
    
        TopProductResponseDTO product2 =  TopProductResponseDTO.builder()
            .productName("Product 2")
            .stock(200)
            .branchName("Branch 2")
            .build();

        when(getTopStockProductsByFranchiseUseCase.execute(1L))
                .thenReturn(Flux.just(product1, product2));

        webTestClient.get()
                .uri("/api/v1/franchises/1/top-stock-products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TopProductResponseDTO.class)
                .value(list -> {
                    assertEquals(2, list.size());

                    assertEquals("Product 1", list.get(0).getProductName());
                    assertEquals(100, list.get(0).getStock());
                    assertEquals("Branch 1", list.get(0).getBranchName());

                    assertEquals("Product 2", list.get(1).getProductName());
                    assertEquals(200, list.get(1).getStock());
                    assertEquals("Branch 2", list.get(1).getBranchName());
                });
    }
    @Test
    void shouldAddBranchToFranchise_Returns201() {
        BranchRequestDTO requestDTO = new BranchRequestDTO("New Branch");
        BranchResponseDTO responseDTO = new BranchResponseDTO(
            1L, 
            "New Branch", 
            1L, 
            LocalDateTime.now(), 
            LocalDateTime.now()
        );

        when(addBranchToFranchiseUseCase.execute(any(BranchRequestDTO.class), eq(1L)))
                .thenReturn(Mono.just(responseDTO));

        webTestClient.post()
                .uri("/api/v1/franchises/1/branches")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("New Branch")
                .jsonPath("$.franchiseId").isEqualTo(1);
    }

    @Test
    void shouldUpdateProductStock_Returns200() {
        ProductStockUpdateDTO stockUpdateDTO = new ProductStockUpdateDTO(150);
        ProductResponseDTO responseDTO = new ProductResponseDTO(
            1L, 
            "Product", 
            150, 
            1L, 
            LocalDateTime.now(), 
            LocalDateTime.now()
        );

        when(updateProductStockUseCase.execute(eq(1L), any(ProductStockUpdateDTO.class)))
                .thenReturn(Mono.just(responseDTO));

        webTestClient.put()
                .uri("/api/v1/products/1/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(stockUpdateDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.stock").isEqualTo(150);
    }

    @Test
    void shouldDeleteProduct_Returns204() {
        when(deleteProductUseCase.execute(1L))
                .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/v1/products/1")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();
    }
}
