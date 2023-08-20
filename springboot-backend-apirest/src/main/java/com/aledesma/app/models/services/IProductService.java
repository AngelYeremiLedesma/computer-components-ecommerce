package com.aledesma.app.models.services;

import org.springframework.http.ResponseEntity;

public interface IProductService {

	public ResponseEntity<?> getProductsByCategory(Long cateogryId);
	
	public ResponseEntity<?> getAllProducts();
	
}
