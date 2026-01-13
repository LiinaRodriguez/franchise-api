package com.franchise.api.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class BranchTest {
    @Test
    void shouldCreateProductWithValidData() {
        Branch branch = Branch.builder()
                .id(1L)
                .name("Branch 1")
                .franchiseId(3L)
                .build()
                .validate();

        assertNotNull(branch);
        assertEquals("Branch 1", branch.getName());
    }
}
