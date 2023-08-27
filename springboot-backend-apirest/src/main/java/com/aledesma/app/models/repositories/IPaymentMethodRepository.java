package com.aledesma.app.models.repositories;

import com.aledesma.app.models.entity.PaymentMethod;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IPaymentMethodRepository extends CrudRepository<PaymentMethod,Long> {
    Optional<PaymentMethod> findByName(String name);
}
