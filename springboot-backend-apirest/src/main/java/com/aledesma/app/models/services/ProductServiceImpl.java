package com.aledesma.app.models.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aledesma.app.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.aledesma.app.models.entity.Category;
import com.aledesma.app.models.entity.Product;
import com.aledesma.app.models.repositories.ICategoryRepository;
import com.aledesma.app.models.repositories.IProductRepository;

@Service
public class ProductServiceImpl implements IProductService{

	@Autowired
	IProductRepository productRepository;
	
	@Autowired
	ICategoryRepository categoryRepository;
	
	@Override
	public ResponseEntity<?> getProductsByCategory(Long categoryId) {
		
		Map<String, Object> response = new HashMap<>();

		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("Category Not Found"));
		response.put("category", category);
			
		List<Product> products = productRepository.findByCategoryId(categoryId);
		response.put("products", products);

		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getAllProducts() {
		Map<String, Object> response = new HashMap<>();
		List<Product> products = (List<Product>)productRepository.findAll();
		response.put("products", products);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findProductById(Long productId) {
		Map<String, Object> response = new HashMap<>();
		Product product = productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException("Product Not Found"));
		response.put("products", product);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> searchProductByKeyword(String keyword) {
		Map<String, Object> response = new HashMap<>();
		List<Product> products = productRepository.findByDescriptionOrNameOrBrandContainingIgnoreCase(keyword);
		response.put("products", products);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}

}
