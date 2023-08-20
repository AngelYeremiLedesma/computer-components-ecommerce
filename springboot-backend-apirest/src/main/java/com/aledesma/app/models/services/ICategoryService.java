package com.aledesma.app.models.services;

import org.springframework.http.ResponseEntity;

public interface ICategoryService {
	
	public ResponseEntity<?> getAllCategories();
	
}
