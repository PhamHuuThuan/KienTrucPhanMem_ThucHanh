package com.example.shippingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long orderId;
    
    private String trackingNumber;
    
    private String carrier;
    
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;
    
    private String shippingAddress;
    
    private LocalDateTime shippedDate;
    
    private LocalDateTime estimatedDeliveryDate;
    
    private LocalDateTime deliveredDate;
    
    private LocalDateTime lastUpdated;
} 