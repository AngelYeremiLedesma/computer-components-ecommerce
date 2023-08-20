package com.aledesma.app.models.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.aledesma.app.models.entity.Product;

public interface IProductRepository extends CrudRepository<Product, Long>{

	public List<Product> findByCategoryId(Long id);
	
}
