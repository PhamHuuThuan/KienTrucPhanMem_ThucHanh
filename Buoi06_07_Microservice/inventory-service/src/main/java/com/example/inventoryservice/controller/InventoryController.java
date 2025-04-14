package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.InventoryRequest;
import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.model.InventoryStatus;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryResponse> getInventoryByProductId(@PathVariable Long productId) {
        return inventoryService.getInventoryByProductId(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<InventoryResponse>> getInventoryByStatus(@PathVariable InventoryStatus status) {
        List<InventoryResponse> inventory = inventoryService.getInventoryByStatus(status);
        return inventory.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(inventory);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryResponse>> getLowStockItems(@RequestParam(defaultValue = "5") Integer threshold) {
        List<InventoryResponse> inventory = inventoryService.getLowStockItems(threshold);
        return inventory.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(inventory);
    }

    @PostMapping
    public ResponseEntity<InventoryResponse> addInventoryItem(@RequestBody InventoryRequest inventoryRequest) {
        InventoryResponse inventory = inventoryService.addInventoryItem(inventoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventory);
    }

    @PutMapping("/product/{productId}/quantity")
    public ResponseEntity<InventoryResponse> updateInventoryQuantity(
            @PathVariable Long productId,
            @RequestParam Integer quantityChange) {
        InventoryResponse inventory = inventoryService.updateInventoryQuantity(productId, quantityChange);
        return ResponseEntity.ok(inventory);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<InventoryResponse> updateInventoryStatus(
            @PathVariable Long id,
            @RequestParam InventoryStatus status) {
        InventoryResponse inventory = inventoryService.updateInventoryStatus(id, status);
        return ResponseEntity.ok(inventory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable Long id) {
        inventoryService.deleteInventoryItem(id);
        return ResponseEntity.noContent().build();
    }
} 