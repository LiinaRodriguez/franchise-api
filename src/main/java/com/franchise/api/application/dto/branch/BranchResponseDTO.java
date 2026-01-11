package com.franchise.api.application.dto.branch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BranchResponseDTO {
    private String id;
    private String name;
    private String franchiseId;
}
