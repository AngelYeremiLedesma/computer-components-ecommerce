package com.aledesma.app.models.repositories;

import org.springframework.data.repository.CrudRepository;

import com.aledesma.app.models.entity.Cart;

public interface ICartRepository extends CrudRepository<Cart, Long>{

}
