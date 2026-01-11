package com.franchise.api.infra.adapter.persistence.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Document(collection = "franchises")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class FranchiseDocument {
    @Id
    private String id;
    private String name;
}
