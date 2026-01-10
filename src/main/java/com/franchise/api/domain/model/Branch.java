package com.franchise.api.domain.model;

import com.franchise.api.domain.exception.InvalidAttributeException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter @Getter
@AllArgsConstructor
public class Branch {
    private String id;
    private String name;
    private String franchiseId;

    public void updateName (String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new InvalidAttributeException("Name cannot be null or empty");
        }
        this.name = newName;
    }
}
