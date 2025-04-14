package com.example.paymentservice.service;

import com.example.paymentservice.dto.PaymentRequest;
import com.example.paymentservice.dto.PaymentResponse;
import com.example.paymentservice.model.PaymentStatus;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    List<PaymentResponse> getAllPayments();
    Optional<PaymentResponse> getPaymentById(Long id);
    List<PaymentResponse> getPaymentsByOrderId(Long orderId);
    List<PaymentResponse> getPaymentsByStatus(PaymentStatus status);
    PaymentResponse processPayment(PaymentRequest paymentRequest);
    PaymentResponse updatePaymentStatus(Long id, PaymentStatus status);
    void deletePayment(Long id);
} 