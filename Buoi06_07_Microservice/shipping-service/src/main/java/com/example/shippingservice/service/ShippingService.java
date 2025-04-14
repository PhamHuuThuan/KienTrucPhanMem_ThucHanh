package com.example.shippingservice.service;

import com.example.shippingservice.dto.ShipmentRequest;
import com.example.shippingservice.dto.ShipmentResponse;
import com.example.shippingservice.model.ShipmentStatus;

import java.util.List;
import java.util.Optional;

public interface ShippingService {
    List<ShipmentResponse> getAllShipments();
    Optional<ShipmentResponse> getShipmentById(Long id);
    List<ShipmentResponse> getShipmentsByOrderId(Long orderId);
    Optional<ShipmentResponse> getShipmentByTrackingNumber(String trackingNumber);
    List<ShipmentResponse> getShipmentsByStatus(ShipmentStatus status);
    ShipmentResponse createShipment(ShipmentRequest shipmentRequest);
    ShipmentResponse updateShipmentStatus(Long id, ShipmentStatus status);
    void deleteShipment(Long id);
} 