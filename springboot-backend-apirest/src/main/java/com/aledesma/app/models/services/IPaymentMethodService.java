package com.aledesma.app.models.services;

import org.springframework.http.ResponseEntity;

public interface IPaymentMethodService {

    ResponseEntity<?> getAllPaymentMethods();
}
