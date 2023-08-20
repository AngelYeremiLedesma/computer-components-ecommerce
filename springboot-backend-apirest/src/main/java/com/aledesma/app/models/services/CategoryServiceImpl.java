package com.aledesma.app.models.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.aledesma.app.models.entity.Category;
import com.aledesma.app.models.repositories.ICategoryRepository;

@Service
public class CategoryServiceImpl implements ICategoryService{

	@Autowired
	ICategoryRepository categoryRepository;
	
	@Override
	public ResponseEntity<?> getAllCategories() {
		Map<String,Object> response = new HashMap<>();
		List<String> categories = categoryRepository.findAllCategoryNames();
		response.put("categories", categories);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}

}
