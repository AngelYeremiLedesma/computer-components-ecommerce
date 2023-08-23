package com.aledesma.app.models.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.aledesma.app.dtos.CategoryDto;
import com.aledesma.app.mappers.CategoryMapper;
import com.aledesma.app.models.entity.Category;
import com.aledesma.app.models.repositories.ICategoryRepository;

@Service
public class CategoryServiceImpl implements ICategoryService{

	@Autowired
	ICategoryRepository categoryRepository;
	
	@Override
	public ResponseEntity<?> getAllCategories() {
		Map<String,Object> response = new HashMap<>();
		List<Category> categories = (List<Category>)categoryRepository.findAll();
		List<CategoryDto> categoriesDto = categories.stream().map((category)->{
			return CategoryMapper.INSTANCE.categoryToCategoryDto(category);
			}).collect(Collectors.toList());
				
				
		response.put("categories", categoriesDto);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getAllProductsInCategory(Long categoryId){
		Map<String,Object> response = new HashMap<>();

		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("Category Not Found"));

		response.put("category", category);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}

}
