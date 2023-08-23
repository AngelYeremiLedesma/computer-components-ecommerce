package com.aledesma.app.models.services;

import com.aledesma.app.dtos.CreateUserDto;
import com.aledesma.app.models.entity.Customer;

public interface ICustomerService {

	Customer createNewCustomer(CreateUserDto createUserDto);

}
