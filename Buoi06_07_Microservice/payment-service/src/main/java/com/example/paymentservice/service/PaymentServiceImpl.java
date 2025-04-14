package com.example.paymentservice.service;

import com.example.paymentservice.dto.PaymentRequest;
import com.example.paymentservice.dto.PaymentResponse;
import com.example.paymentservice.model.Payment;
import com.example.paymentservice.model.PaymentStatus;
import com.example.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::mapToPaymentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PaymentResponse> getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .map(this::mapToPaymentResponse);
    }

    @Override
    public List<PaymentResponse> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .stream()
                .map(this::mapToPaymentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponse> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status)
                .stream()
                .map(this::mapToPaymentResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        log.info("Processing payment for order ID: {}", paymentRequest.getOrderId());
        
        try {
            // In a real implementation, you would integrate with a payment gateway
            // For now, we'll simulate payment processing
            
            Payment payment = Payment.builder()
                    .orderId(paymentRequest.getOrderId())
                    .amount(paymentRequest.getAmount())
                    .paymentMethod(paymentRequest.getPaymentMethod())
                    .paymentDate(LocalDateTime.now())
                    .status(PaymentStatus.COMPLETED) // Simulate successful payment
                    .transactionId(generateTransactionId())
                    .lastUpdated(LocalDateTime.now())
                    .build();
            
            Payment savedPayment = paymentRepository.save(payment);
            log.info("Payment processed successfully with transaction ID: {}", savedPayment.getTransactionId());
            
            return mapToPaymentResponse(savedPayment);
        } catch (Exception e) {
            log.error("Error processing payment: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public PaymentResponse updatePaymentStatus(Long id, PaymentStatus status) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));
        
        payment.setStatus(status);
        payment.setLastUpdated(LocalDateTime.now());
        
        Payment updatedPayment = paymentRepository.save(payment);
        return mapToPaymentResponse(updatedPayment);
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));
        paymentRepository.delete(payment);
    }
    
    private PaymentResponse mapToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .paymentMethod(payment.getPaymentMethod())
                .transactionId(payment.getTransactionId())
                .paymentDate(payment.getPaymentDate())
                .build();
    }
    
    private String generateTransactionId() {
        return UUID.randomUUID().toString();
    }
} 