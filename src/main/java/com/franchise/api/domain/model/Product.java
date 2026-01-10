package com.franchise.api.domain.model;

import com.franchise.api.domain.exception.InvalidAttributeException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Setter @Getter
@AllArgsConstructor
public class Product {
    private String id;
    private String name;
    private Integer stock;
    private String branchId;

    public void updateStock (Integer newStock) {
       if (newStock < 0) {
        throw new InvalidAttributeException("Stock cannot be negative");
    }
    this.stock = newStock;
    }

    public void updateName (String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new InvalidAttributeException("Name cannot be null or empty");
        }
        this.name = newName;
    }

}
