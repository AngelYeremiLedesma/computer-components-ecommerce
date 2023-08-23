package com.aledesma.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aledesma.app.models.services.ICategoryService;

@RestController
@RequestMapping(path = "/categories")
public class CategoryRestController {

	@Autowired
	ICategoryService categoryService;
	
	@GetMapping(path = "")
	@PreAuthorize("permitAll()")
	public ResponseEntity<?> getListedCategories() {
		return categoryService.getAllCategories();
	}

	@GetMapping(path = "/{categoryId}")
	@PreAuthorize("permitAll()")
	public ResponseEntity<?> getProductsByCategory(@PathVariable Long categoryId){
		return categoryService.getAllProductsInCategory(categoryId);
	}
}
