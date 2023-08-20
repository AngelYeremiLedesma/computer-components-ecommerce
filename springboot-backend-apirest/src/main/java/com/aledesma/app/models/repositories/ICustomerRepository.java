package com.aledesma.app.models.repositories;

import org.springframework.data.repository.CrudRepository;

import com.aledesma.app.models.entity.Customer;

public interface ICustomerRepository extends CrudRepository<Customer, Long>{

}
