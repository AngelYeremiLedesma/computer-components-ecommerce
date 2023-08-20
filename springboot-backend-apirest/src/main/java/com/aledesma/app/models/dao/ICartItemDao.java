package com.aledesma.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.aledesma.app.models.entity.CartItem;

public interface ICartItemDao extends CrudRepository<CartItem, Long>{

}
