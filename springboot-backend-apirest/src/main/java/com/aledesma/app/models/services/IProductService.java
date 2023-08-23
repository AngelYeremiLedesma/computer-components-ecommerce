package com.aledesma.app.models.services;

import org.springframework.http.ResponseEntity;

public interface IProductService {

	ResponseEntity<?> getProductsByCategory(Long cateogryId);
	
	ResponseEntity<?> getAllProducts();

    ResponseEntity<?> findProductById(Long productId);

	ResponseEntity<?> searchProductByKeyword(String keyword);
}
