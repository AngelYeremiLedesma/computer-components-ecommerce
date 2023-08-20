package com.aledesma.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.aledesma.app.models.entity.Category;

public interface ICategoryDao extends CrudRepository<Category, Long>{

	@Query ("select c.name from Category c")
	 List<String> findAllCategoryNames();
}
