package com.example.orderservice.service;

import com.example.orderservice.dto.*;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderItem;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Value("${customer.service.url}")
    private String customerServiceUrl;

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderResponse> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::mapToOrderResponse);
    }

    @Override
    public List<OrderResponse> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // Validate customer exists
        CustomerDto customer = webClientBuilder.build()
                .get()
                .uri(customerServiceUrl + "/api/customers/" + orderRequest.getCustomerId())
                .retrieve()
                .bodyToMono(CustomerDto.class)
                .block();

        if (customer == null) {
            throw new EntityNotFoundException("Customer not found with id: " + orderRequest.getCustomerId());
        }

        Order order = new Order();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        BigDecimal totalAmount = BigDecimal.ZERO;

        List<OrderItem> orderItems = orderRequest.getOrderItems().stream()
                .map(itemDto -> {
                    // Validate product exists and has enough inventory
                    ProductDto product = webClientBuilder.build()
                            .get()
                            .uri(productServiceUrl + "/api/products/" + itemDto.getProductId())
                            .retrieve()
                            .bodyToMono(ProductDto.class)
                            .block();

                    if (product == null) {
                        throw new EntityNotFoundException("Product not found with id: " + itemDto.getProductId());
                    }

                    if (product.getQuantity() < itemDto.getQuantity()) {
                        throw new IllegalArgumentException("Not enough inventory for product: " + product.getName());
                    }

                    // Update product inventory
                    product.setQuantity(product.getQuantity() - itemDto.getQuantity());
                    webClientBuilder.build()
                            .put()
                            .uri(productServiceUrl + "/api/products/" + product.getId())
                            .bodyValue(product)
                            .retrieve()
                            .bodyToMono(ProductDto.class)
                            .block();

                    OrderItem orderItem = new OrderItem();
                    orderItem.setProductId(itemDto.getProductId());
                    orderItem.setQuantity(itemDto.getQuantity());
                    orderItem.setPrice(product.getPrice());
                    orderItem.setOrder(order);

                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);

        // Calculate total amount
        totalAmount = orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        return mapToOrderResponse(savedOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
        orderRepository.delete(order);
    }

    private OrderResponse mapToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setCustomerId(order.getCustomerId());
        response.setOrderDate(order.getOrderDate());
        response.setStatus(order.getStatus());
        response.setTotalAmount(order.getTotalAmount());

        // Try to get customer name
        try {
            CustomerDto customer = webClientBuilder.build()
                    .get()
                    .uri(customerServiceUrl + "/api/customers/" + order.getCustomerId())
                    .retrieve()
                    .bodyToMono(CustomerDto.class)
                    .block();

            if (customer != null) {
                response.setCustomerName(customer.getName());
            }
        } catch (Exception e) {
            // If customer service is not available, continue without customer name
            response.setCustomerName("Unknown");
        }

        List<OrderItemDto> orderItemDtos = order.getOrderItems().stream()
                .map(this::mapToOrderItemDto)
                .collect(Collectors.toList());

        response.setOrderItems(orderItemDtos);

        return response;
    }

    private OrderItemDto mapToOrderItemDto(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getProductId(),
                orderItem.getQuantity(),
                orderItem.getPrice());
    }
}