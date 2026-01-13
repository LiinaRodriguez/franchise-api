package com.franchise.api.application.dto.product;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Integer stock;
    private Long branchId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
