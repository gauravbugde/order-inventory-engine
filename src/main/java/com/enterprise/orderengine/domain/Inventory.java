package com.enterprise.orderengine.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventory")
@Getter @Setter @NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String skuCode;

    @Min(0)
    @Column(nullable = false)
    private Integer availableQuantity;

    @Version
    private Long version; // Optimistic locking to prevent concurrent inventory overselling
}
