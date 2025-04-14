package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.InventoryRequest;
import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.model.InventoryStatus;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    List<InventoryResponse> getAllInventory();
    Optional<InventoryResponse> getInventoryById(Long id);
    Optional<InventoryResponse> getInventoryByProductId(Long productId);
    List<InventoryResponse> getInventoryByStatus(InventoryStatus status);
    List<InventoryResponse> getLowStockItems(Integer threshold);
    InventoryResponse addInventoryItem(InventoryRequest inventoryRequest);
    InventoryResponse updateInventoryQuantity(Long productId, Integer quantityChange);
    InventoryResponse updateInventoryStatus(Long id, InventoryStatus status);
    void deleteInventoryItem(Long id);
} 