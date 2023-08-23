package com.aledesma.app.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aledesma.app.dtos.CreateUserDto;
import com.aledesma.app.models.entity.Cart;
import com.aledesma.app.models.entity.Customer;
import com.aledesma.app.models.repositories.ICartRepository;
import com.aledesma.app.models.repositories.ICustomerRepository;

@Service
public class CustomerServiceImpl implements ICustomerService{

	@Autowired
	ICartRepository cartRepository;
	
	@Autowired
	ICustomerRepository customerRepository;
	
	@Override
	public Customer createNewCustomer(CreateUserDto createUserDto) {
		Cart cart = Cart.builder().build();
		Customer customer = Customer.builder().email(createUserDto.getEmail()).cart(cart).build();
		cartRepository.save(cart);
		customerRepository.save(customer);
		
		return customer;
	}
}
