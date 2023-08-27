package com.aledesma.app.dtos;

import com.aledesma.app.models.entity.Cart;
import com.aledesma.app.models.entity.Customer;
import com.aledesma.app.models.entity.EPaymentMethod;
import com.aledesma.app.validation.PaymentMethodValidation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDto {

    @NotBlank(message = "{not.blank}")
    private String address;

    @NotBlank(message = "{not.blank}")
    @PaymentMethodValidation(message = "{valid.payment.method}")
    private String paymentMethod;

}
