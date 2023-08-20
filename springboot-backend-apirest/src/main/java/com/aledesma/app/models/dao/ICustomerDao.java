package com.aledesma.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.aledesma.app.models.entity.Customer;

public interface ICustomerDao extends CrudRepository<Customer, Long>{

}
