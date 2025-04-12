package com.example.orderservice.service;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderResponse> getAllOrders();

    Optional<OrderResponse> getOrderById(Long id);

    List<OrderResponse> getOrdersByCustomerId(Long customerId);

    OrderResponse createOrder(OrderRequest orderRequest);

    void deleteOrder(Long id);
}