package com.aledesma.app.controllers;

import com.aledesma.app.models.services.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping(path = "/{productId}")
	@PreAuthorize("permitAll()")
	public ResponseEntity<?> showProductById(@RequestParam Long productId){
		return productService.findProductById(productId);
	}

	@GetMapping(path = "/search")
	@PreAuthorize("permitAll()")
	public ResponseEntity<?> searchProducts(@RequestParam String keyword){
		return productService.searchProductByKeyword(keyword);
	}

}
