package com.aledesma.app.models.services;

import com.aledesma.app.models.entity.EPaymentMethod;
import com.aledesma.app.models.entity.PaymentMethod;
import com.aledesma.app.models.repositories.IPaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentMethodServiceImpl implements IPaymentMethodService{

    @Override
    public ResponseEntity<?> getAllPaymentMethods(){
        Map<String, Object> response = new HashMap<>();
        List<String> paymentMethods = Arrays.stream(EPaymentMethod.values())
                .map((ePaymentMethod -> ePaymentMethod.getDisplayName()))
                .collect(Collectors.toList());
        response.put("PaymentMethods", paymentMethods);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
