package com.franchise.api.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class FranchiseTest {
    @Test
    void shouldCreateProductWithValidData() {
        Franchise franchise = Franchise.builder()
                .id(1L)
                .name("Franchise 1")
                .build()
                .validate();

        assertNotNull(franchise);
        assertEquals("Franchise 1", franchise.getName());
    }
}
