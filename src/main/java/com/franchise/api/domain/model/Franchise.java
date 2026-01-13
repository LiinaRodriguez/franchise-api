package com.franchise.api.domain.model;

import java.time.LocalDateTime;

import com.franchise.api.domain.exception.InvalidAttributeException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class Franchise {
    private final Long id;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    

    public Franchise validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidAttributeException( "name", "Name cannot be null or empty");
        }
        return this;
    }
}
