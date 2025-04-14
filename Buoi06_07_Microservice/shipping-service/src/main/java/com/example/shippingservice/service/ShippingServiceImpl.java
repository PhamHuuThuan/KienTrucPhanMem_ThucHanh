package com.example.shippingservice.service;

import com.example.shippingservice.dto.ShipmentRequest;
import com.example.shippingservice.dto.ShipmentResponse;
import com.example.shippingservice.model.Shipment;
import com.example.shippingservice.model.ShipmentStatus;
import com.example.shippingservice.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingServiceImpl implements ShippingService {

    private final ShipmentRepository shipmentRepository;

    @Override
    public List<ShipmentResponse> getAllShipments() {
        return shipmentRepository.findAll()
                .stream()
                .map(this::mapToShipmentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ShipmentResponse> getShipmentById(Long id) {
        return shipmentRepository.findById(id)
                .map(this::mapToShipmentResponse);
    }

    @Override
    public List<ShipmentResponse> getShipmentsByOrderId(Long orderId) {
        return shipmentRepository.findByOrderId(orderId)
                .stream()
                .map(this::mapToShipmentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ShipmentResponse> getShipmentByTrackingNumber(String trackingNumber) {
        return shipmentRepository.findByTrackingNumber(trackingNumber)
                .map(this::mapToShipmentResponse);
    }

    @Override
    public List<ShipmentResponse> getShipmentsByStatus(ShipmentStatus status) {
        return shipmentRepository.findByStatus(status)
                .stream()
                .map(this::mapToShipmentResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ShipmentResponse createShipment(ShipmentRequest shipmentRequest) {
        log.info("Creating shipment for order ID: {}", shipmentRequest.getOrderId());
        
        Shipment shipment = Shipment.builder()
                .orderId(shipmentRequest.getOrderId())
                .carrier(shipmentRequest.getCarrier())
                .trackingNumber(generateTrackingNumber())
                .status(ShipmentStatus.PROCESSING)
                .shippingAddress(shipmentRequest.getShippingAddress())
                .shippedDate(null) // Will be set when status changes to SHIPPING
                .estimatedDeliveryDate(LocalDateTime.now().plusDays(5)) // Estimate 5 days for delivery
                .deliveredDate(null) // Will be set when delivered
                .lastUpdated(LocalDateTime.now())
                .build();
        
        Shipment savedShipment = shipmentRepository.save(shipment);
        log.info("Created shipment with tracking number: {}", savedShipment.getTrackingNumber());
        
        return mapToShipmentResponse(savedShipment);
    }

    @Override
    @Transactional
    public ShipmentResponse updateShipmentStatus(Long id, ShipmentStatus status) {
        log.info("Updating shipment status for ID: {} to {}", id, status);
        
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found with id: " + id));
        
        shipment.setStatus(status);
        shipment.setLastUpdated(LocalDateTime.now());
        
        // Update relevant date fields based on status
        switch (status) {
            case SHIPPING:
                shipment.setShippedDate(LocalDateTime.now());
                break;
            case DELIVERED:
                shipment.setDeliveredDate(LocalDateTime.now());
                break;
            default:
                // No additional updates needed for other statuses
                break;
        }
        
        Shipment updatedShipment = shipmentRepository.save(shipment);
        return mapToShipmentResponse(updatedShipment);
    }

    @Override
    public void deleteShipment(Long id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found with id: " + id));
        shipmentRepository.delete(shipment);
    }
    
    private ShipmentResponse mapToShipmentResponse(Shipment shipment) {
        return ShipmentResponse.builder()
                .id(shipment.getId())
                .orderId(shipment.getOrderId())
                .trackingNumber(shipment.getTrackingNumber())
                .carrier(shipment.getCarrier())
                .status(shipment.getStatus())
                .shippingAddress(shipment.getShippingAddress())
                .shippedDate(shipment.getShippedDate())
                .estimatedDeliveryDate(shipment.getEstimatedDeliveryDate())
                .deliveredDate(shipment.getDeliveredDate())
                .build();
    }
    
    private String generateTrackingNumber() {
        // Generate a random tracking number for demonstration
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        
        // Carrier prefix (e.g., "SP" for our shipping provider)
        sb.append("SP");
        
        // Add 10 random digits
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        
        return sb.toString();
    }
} 