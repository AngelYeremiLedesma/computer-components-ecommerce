package com.aledesma.app.models.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
		
		try {
			Category category = categoryRepository.findById(categoryId).orElse(null);
			
			if(category==null) {
				response.put("error", "Category not found");
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
			}
			
			response.put("category", category);
			
			/*
			 * List<Product> products = productDao.findByCategoryId(categoryId);
			 * response.put("products", products);
			 */
			
		}catch(DataAccessException e) {
			response.put("error", e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getAllProducts() {
		Map<String, Object> response = new HashMap<>();
		List<Product> products = (List<Product>)productRepository.findAll();
		response.put("products", products);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}

}
