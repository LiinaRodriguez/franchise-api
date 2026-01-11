package com.franchise.api.infra.adapter.persistence.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Document(collection = "branches")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BranchDocument {
    @Id
    private String id;
    private String name;
    private String franchiseId;
}