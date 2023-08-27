package com.aledesma.app.controllers;

import com.aledesma.app.dtos.CreateOrderDto;
import com.aledesma.app.models.services.IOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping(path = "/orders")
@RestController
public class OrderRestController {

    @Autowired
    IOrderService orderService;

    @GetMapping(path = "/{orderId}")
    @PreAuthorize("@authenticatedUserService.isAdmin()")
    public ResponseEntity<?> showOrderById(@PathVariable Long orderId){
        return orderService.getOrderById(orderId);
    }

    @GetMapping(path = "")
    @PreAuthorize("@authenticatedUserService.isAdmin()")
    public ResponseEntity<?> showAllOrders(){
        return orderService.getAllOrders();
    }

    @PostMapping(path = "/customer/{customerId}")
    @PreAuthorize("@authenticatedUserService.hasCustomerIdOrIsAdmin(#customerId)")
    public ResponseEntity<?> createOrderByCustomerId(@PathVariable Long customerId, @Valid @RequestBody CreateOrderDto createOrderDto, BindingResult bindingResult){
        Map<String, Object> response = new HashMap<>();
        if(bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> "The field ".concat(error.getField()).concat(" ").concat(error.getDefaultMessage()))
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
        }
        return orderService.saveOrder(customerId,createOrderDto);
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("@authenticatedUserService.hasCustomerIdOrIsAdmin(#customerId)")
    public ResponseEntity<?> getCustomerOrders(@PathVariable Long customerId) {
        return orderService.getCustomerOrders(customerId);
    }

}
