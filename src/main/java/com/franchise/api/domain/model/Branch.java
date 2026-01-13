package com.franchise.api.domain.model;

import java.time.LocalDateTime;

import com.franchise.api.domain.exception.InvalidAttributeException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Setter @Getter
@AllArgsConstructor
public class Branch {
    private final Long id;
    private final String name;
    private final Long franchiseId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Branch validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidAttributeException("name", "Branch name cannot be null or empty");
        }
        if (franchiseId == null || franchiseId <= 0) {
            throw new InvalidAttributeException("franchiseId", "Branch must belong to a franchise");
        }
        return this;
    }
}
