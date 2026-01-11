package com.franchise.api.application.dto.franchise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FranchiseRequestDTO {
    private String name;
}
