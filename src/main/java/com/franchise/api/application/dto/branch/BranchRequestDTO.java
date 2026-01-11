package com.franchise.api.application.dto.branch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BranchRequestDTO {
    private String name;
}