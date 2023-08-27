package com.aledesma.app.models.services;

import com.aledesma.app.dtos.CreateOrderDto;
import com.aledesma.app.exceptions.CustomerNotFoundException;
import com.aledesma.app.exceptions.ProductNotFoundException;
import com.aledesma.app.models.entity.*;
import com.aledesma.app.models.repositories.ICustomerRepository;
import com.aledesma.app.models.repositories.IOrderRepository;
import com.aledesma.app.models.repositories.IPaymentMethodRepository;
import com.aledesma.app.models.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements IOrderService{

    @Autowired
    ICartService cartService;

    @Autowired
    IOrderRepository orderRepository;

    @Autowired
    ICustomerRepository customerRepository;

    @Autowired
    IPaymentMethodRepository paymentMethodRepository;

    @Autowired
    IProductRepository productRepository;

    @Override
    public ResponseEntity<?> saveOrder(Long customerId, CreateOrderDto createOrderDto) {
        Map<String, Object> response = new HashMap<>();

        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer Not Found"));

        Cart cart = cartService.findCartByCustomerId(customerId);
        List<CartItem> cartItems = cart.getItems();

        if(cartItems==null){
            throw new RuntimeException("Empty cart");
        }



        PaymentMethod paymentMethod = PaymentMethod.builder().name(createOrderDto.getPaymentMethod()).build();
        paymentMethodRepository.save(paymentMethod);

        Order order = Order.builder().address(createOrderDto.getAddress()).customer(customer).cart(cart).cart(cart)
                .paymentMethod(paymentMethod)
                .build();

        orderRepository.save(order);

        for(CartItem item: cartItems){
            Product product = productRepository.findById(item.getProduct().getId()).orElseThrow(()-> new ProductNotFoundException("Product Not Found"));
            if((product.getAvailableStock()-item.getQuantity())>0){
                product.setAvailableStock(product.getAvailableStock()-item.getQuantity());
            }
            else{
                throw new RuntimeException("Stock no available for this action");
            }
            productRepository.save(product);
        }

        response.put("order", order);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> getCustomerOrders(Long customerId){
        Map<String, Object> response = new HashMap<>();
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        response.put("orders",orders);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getOrderById(Long orderId) {
        Map<String, Object> response = new HashMap<>();
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order Not Found"));
        response.put("order",order);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllOrders() {
        Map<String, Object> response = new HashMap<>();
        List<Order> orders = (List<Order>)orderRepository.findAll();
        response.put("orders",orders);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
