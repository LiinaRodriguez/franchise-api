package com.franchise.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Setter @Getter
@AllArgsConstructor
public class Branch {
    private String id;
    private String name;
    private List<Product> products = new ArrayList<>();
}
