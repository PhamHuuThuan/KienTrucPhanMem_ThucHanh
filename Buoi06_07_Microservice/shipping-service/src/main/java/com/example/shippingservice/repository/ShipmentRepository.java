package com.example.shippingservice.repository;

import com.example.shippingservice.model.Shipment;
import com.example.shippingservice.model.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByOrderId(Long orderId);
    List<Shipment> findByStatus(ShipmentStatus status);
    Optional<Shipment> findByTrackingNumber(String trackingNumber);
} 