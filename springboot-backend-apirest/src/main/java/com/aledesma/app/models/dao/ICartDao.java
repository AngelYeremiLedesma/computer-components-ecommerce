package com.aledesma.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.aledesma.app.models.entity.Cart;

public interface ICartDao extends CrudRepository<Cart, Long>{

}
