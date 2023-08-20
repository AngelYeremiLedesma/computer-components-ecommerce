package com.aledesma.app.models.repositories;

import org.springframework.data.repository.CrudRepository;

import com.aledesma.app.models.entity.CartItem;

public interface ICartItemRepository extends CrudRepository<CartItem, Long>{

}
