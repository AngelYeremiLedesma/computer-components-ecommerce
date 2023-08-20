package com.aledesma.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aledesma.app.models.services.IProductService;

@RestController
@RequestMapping(path = "/products")
public class ProductRestController {
	
	@Autowired
	IProductService productService;
	
	@GetMapping(path="")
	@PreAuthorize("permitAll()")
	public ResponseEntity<?> showProducts(@RequestParam(name = "category", required = false) Long categoryId){
		if(categoryId!=null) {
			return productService.getProductsByCategory(categoryId);
		}
			return productService.getAllProducts();
	}

}
