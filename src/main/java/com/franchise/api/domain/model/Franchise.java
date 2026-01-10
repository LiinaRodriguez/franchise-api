package com.franchise.api.domain.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Setter @Getter
@AllArgsConstructor
public class Franchise {
    private String id;
    private String name;
    private List<Branch> branches = new ArrayList<>();
    
}
