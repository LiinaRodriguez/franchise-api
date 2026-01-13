package com.franchise.api.infra.adapter.persistence.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@Table("branches")
public class BranchEntity {
    @Id
    private Long id;

    @Column("franchise_id")
    private Long franchiseId;

    private String name;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
