package com.franchise.api.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.franchise.api.domain.exception.InvalidAttributeException;

public class ProductTest {
    @Test
    void shouldCreateProductWithValidData() {
        Product product = Product.builder()
                .id(1L)
                .name("Coca Cola")
                .stock(50)
                .branchId(2L)
                .build()
                .validate();

        assertNotNull(product);
        assertEquals("Coca Cola", product.getName());
        assertEquals(50, product.getStock());
    }

    @Test
    void shouldUpdateStockUsingToBuilder() {
        Product product = Product.builder().name("Original").stock(10).branchId(2L).build().validate();
        
        Product updatedProduct = product.toBuilder()
                .stock(20)
                .build();

        assertEquals(10, product.getStock());
        assertEquals(20, updatedProduct.getStock());
    }

    @Test 
    void shouldHandleZeroStock() {
        Product product = Product.builder()
                .id(2L)
                .name("Pepsi")
                .stock(0)
                .branchId(2L)
                .build();

        assertNotNull(product);
        assertEquals(0, product.getStock());
    }

    @Test 
    void shouldUpdateNameUsingToBuilder() {
        Product product = Product.builder().name("Fanta").stock(15).branchId(2L).build().validate();
        
        Product updatedProduct = product.toBuilder()
                .name("Fanta Orange")
                .build();

        assertEquals("Fanta", product.getName());
        assertEquals("Fanta Orange", updatedProduct.getName());
    }

    @Test
    void shouldThrowExceptionWhenStockIsNegative() {
        assertThrows(InvalidAttributeException.class, () -> {
            Product.builder()
                    .id(3L)
                    .name("Sprite")
                    .stock(-5)
                    .branchId(2L)
                    .build()
                    .validate();
        });
    }   
}
