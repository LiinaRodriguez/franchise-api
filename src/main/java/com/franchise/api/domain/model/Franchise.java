package com.franchise.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;

@Builder(toBuilder = true)
@Setter @Getter
@AllArgsConstructor
public class Franchise {
    private String id;
    private String name;
    

    public void updateName (String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = newName;
    }
}
