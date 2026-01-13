package com.franchise.api.domain.model;

import java.time.LocalDateTime;

import com.franchise.api.domain.exception.InvalidAttributeException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class Product {
    private final Long id;
    private final String name;
    private final Integer stock;
    private final Long branchId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Product validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidAttributeException("name", "Product name cannot be null or empty");
        }
        if (stock == null || stock < 0) {
            throw new InvalidAttributeException("stock", "Stock cannot be negative");
        }
        if (branchId == null || branchId.longValue()<= 0) {
            throw new InvalidAttributeException("branchId", "Product must belong to a branch");
        }
        return this;
    }

}
