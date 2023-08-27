package com.aledesma.app.controllers;

import com.aledesma.app.models.services.IPaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/payment-methods")
public class PaymentMethodRestController {

    @Autowired
    IPaymentMethodService paymentMethodService;

    @GetMapping(path = "")
    @PreAuthorize("@authenticatedUserService.permitAll()")
    public ResponseEntity<?> showAllPaymentMethods(){
        return paymentMethodService.getAllPaymentMethods();
    }
}
