package com.franchise.api.application.dto.branch;

import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
