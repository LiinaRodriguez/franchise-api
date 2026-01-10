package com.franchise.api.domain.model;

import com.franchise.api.domain.exception.InvalidAttributeException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class Product {
    private final String id;
    private final String name;
    private final Integer stock;
    private final String branchId;

    public Product validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidAttributeException("name", "Product name cannot be null or empty");
        }
        if (stock == null || stock < 0) {
            throw new InvalidAttributeException("stock", "Stock cannot be negative");
        }
        if (branchId == null || branchId.trim().isEmpty()) {
            throw new InvalidAttributeException("branchId", "Product must belong to a branch");
        }
        return this;
    }

}
