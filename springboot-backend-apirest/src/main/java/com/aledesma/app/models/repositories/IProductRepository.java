package com.aledesma.app.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.aledesma.app.models.entity.Product;
import org.springframework.data.repository.query.Param;

public interface IProductRepository extends CrudRepository<Product, Long>{

	public List<Product> findByCategoryId(Long id);

	@Query("SELECT p FROM Product p WHERE " +
			"LOWER(p.description) LIKE %:keyword% OR " +
			"LOWER(p.name) LIKE %:keyword% OR " +
			"LOWER(p.brand) LIKE %:keyword%")
	List<Product> findByDescriptionOrNameOrBrandContainingIgnoreCase(@Param("keyword") String keyword);
	
}
