package com.example.shippingservice.dto;

import com.example.shippingservice.model.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentResponse {
    private Long id;
    private Long orderId;
    private String trackingNumber;
    private String carrier;
    private ShipmentStatus status;
    private String shippingAddress;
    private LocalDateTime shippedDate;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime deliveredDate;
} 