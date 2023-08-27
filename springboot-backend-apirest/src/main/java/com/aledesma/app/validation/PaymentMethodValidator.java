package com.aledesma.app.validation;

import com.aledesma.app.models.entity.EPaymentMethod;
import com.aledesma.app.models.entity.ERole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class PaymentMethodValidator implements ConstraintValidator<PaymentMethodValidation,String> {
    @Override
    public boolean isValid(String paymentMethod, ConstraintValidatorContext context) {
        Set<String> validPaymentMethods = Arrays.stream(EPaymentMethod.values())
                .map(EPaymentMethod::getDisplayName)
                .collect(Collectors.toSet());

        if(validPaymentMethods.contains(paymentMethod)){
            return true;
        }
        return false;
    }
}
