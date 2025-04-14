package com.example.shippingservice.controller;

import com.example.shippingservice.dto.ShipmentRequest;
import com.example.shippingservice.dto.ShipmentResponse;
import com.example.shippingservice.model.ShipmentStatus;
import com.example.shippingservice.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    @GetMapping
    public ResponseEntity<List<ShipmentResponse>> getAllShipments() {
        return ResponseEntity.ok(shippingService.getAllShipments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentResponse> getShipmentById(@PathVariable Long id) {
        return shippingService.getShipmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<ShipmentResponse>> getShipmentsByOrderId(@PathVariable Long orderId) {
        List<ShipmentResponse> shipments = shippingService.getShipmentsByOrderId(orderId);
        return shipments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(shipments);
    }

    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<ShipmentResponse> getShipmentByTrackingNumber(@PathVariable String trackingNumber) {
        return shippingService.getShipmentByTrackingNumber(trackingNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ShipmentResponse>> getShipmentsByStatus(@PathVariable ShipmentStatus status) {
        List<ShipmentResponse> shipments = shippingService.getShipmentsByStatus(status);
        return shipments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(shipments);
    }

    @PostMapping
    public ResponseEntity<ShipmentResponse> createShipment(@RequestBody ShipmentRequest shipmentRequest) {
        ShipmentResponse shipment = shippingService.createShipment(shipmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(shipment);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ShipmentResponse> updateShipmentStatus(
            @PathVariable Long id,
            @RequestParam ShipmentStatus status) {
        ShipmentResponse shipment = shippingService.updateShipmentStatus(id, status);
        return ResponseEntity.ok(shipment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
        shippingService.deleteShipment(id);
        return ResponseEntity.noContent().build();
    }
} 