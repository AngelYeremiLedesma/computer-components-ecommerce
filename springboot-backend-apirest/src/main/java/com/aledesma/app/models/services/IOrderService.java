package com.aledesma.app.models.services;

import com.aledesma.app.dtos.CreateOrderDto;
import org.springframework.http.ResponseEntity;

public interface IOrderService {
    ResponseEntity<?> saveOrder(Long customerId, CreateOrderDto createOrderDto);

    ResponseEntity<?> getCustomerOrders(Long customerId);

    ResponseEntity<?> getOrderById(Long orderId);

    ResponseEntity<?> getAllOrders();
}
