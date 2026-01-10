package com.franchise.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
public class Product {
    private String id;
    private String name;
    private String stock;
}
