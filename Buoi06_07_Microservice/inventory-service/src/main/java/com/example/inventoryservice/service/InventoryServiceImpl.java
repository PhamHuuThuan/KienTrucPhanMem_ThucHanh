package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.InventoryRequest;
import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.model.InventoryItem;
import com.example.inventoryservice.model.InventoryStatus;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public List<InventoryResponse> getAllInventory() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::mapToInventoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<InventoryResponse> getInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .map(this::mapToInventoryResponse);
    }

    @Override
    public Optional<InventoryResponse> getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .map(this::mapToInventoryResponse);
    }

    @Override
    public List<InventoryResponse> getInventoryByStatus(InventoryStatus status) {
        return inventoryRepository.findByStatus(status)
                .stream()
                .map(this::mapToInventoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryResponse> getLowStockItems(Integer threshold) {
        return inventoryRepository.findByQuantityLessThan(threshold)
                .stream()
                .map(this::mapToInventoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InventoryResponse addInventoryItem(InventoryRequest inventoryRequest) {
        log.info("Adding inventory item for product ID: {}", inventoryRequest.getProductId());
        
        // Check if product already exists in inventory
        Optional<InventoryItem> existingItem = inventoryRepository.findByProductId(inventoryRequest.getProductId());
        
        if (existingItem.isPresent()) {
            // Update existing inventory
            InventoryItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + inventoryRequest.getQuantity());
            item.setLastUpdated(LocalDateTime.now());
            
            // Update status based on new quantity
            updateInventoryStatus(item);
            
            InventoryItem updatedItem = inventoryRepository.save(item);
            log.info("Updated existing inventory for product ID: {}", updatedItem.getProductId());
            
            return mapToInventoryResponse(updatedItem);
        } else {
            // Create new inventory item
            InventoryItem newItem = InventoryItem.builder()
                    .productId(inventoryRequest.getProductId())
                    .quantity(inventoryRequest.getQuantity())
                    .location(inventoryRequest.getLocation())
                    .lastUpdated(LocalDateTime.now())
                    .build();
            
            // Set status based on quantity
            if (inventoryRequest.getQuantity() > 0) {
                newItem.setStatus(InventoryStatus.IN_STOCK);
            } else {
                newItem.setStatus(InventoryStatus.OUT_OF_STOCK);
            }
            
            InventoryItem savedItem = inventoryRepository.save(newItem);
            log.info("Added new inventory item for product ID: {}", savedItem.getProductId());
            
            return mapToInventoryResponse(savedItem);
        }
    }

    @Override
    @Transactional
    public InventoryResponse updateInventoryQuantity(Long productId, Integer quantityChange) {
        log.info("Updating inventory quantity for product ID: {} by {}", productId, quantityChange);
        
        InventoryItem item = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found for product id: " + productId));
        
        // Update quantity
        item.setQuantity(item.getQuantity() + quantityChange);
        item.setLastUpdated(LocalDateTime.now());
        
        // Update status based on new quantity
        updateInventoryStatus(item);
        
        InventoryItem updatedItem = inventoryRepository.save(item);
        log.info("Updated inventory quantity for product ID: {}. New quantity: {}", productId, updatedItem.getQuantity());
        
        return mapToInventoryResponse(updatedItem);
    }

    @Override
    @Transactional
    public InventoryResponse updateInventoryStatus(Long id, InventoryStatus status) {
        log.info("Updating inventory status for ID: {} to {}", id, status);
        
        InventoryItem item = inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found with id: " + id));
        
        item.setStatus(status);
        item.setLastUpdated(LocalDateTime.now());
        
        InventoryItem updatedItem = inventoryRepository.save(item);
        return mapToInventoryResponse(updatedItem);
    }

    @Override
    public void deleteInventoryItem(Long id) {
        InventoryItem item = inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found with id: " + id));
        inventoryRepository.delete(item);
    }
    
    private InventoryResponse mapToInventoryResponse(InventoryItem item) {
        return InventoryResponse.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .location(item.getLocation())
                .status(item.getStatus())
                .lastUpdated(item.getLastUpdated())
                .build();
    }
    
    private void updateInventoryStatus(InventoryItem item) {
        if (item.getQuantity() <= 0) {
            item.setStatus(InventoryStatus.OUT_OF_STOCK);
        } else if (item.getQuantity() < 5) { // Arbitrary threshold, could be configurable
            item.setStatus(InventoryStatus.LOW_STOCK);
        } else {
            item.setStatus(InventoryStatus.IN_STOCK);
        }
    }
} 