package com.example.inventoryservice.dto;

import com.example.inventoryservice.model.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {
    private Long id;
    private Long productId;
    private Integer quantity;
    private String location;
    private InventoryStatus status;
    private LocalDateTime lastUpdated;
} 