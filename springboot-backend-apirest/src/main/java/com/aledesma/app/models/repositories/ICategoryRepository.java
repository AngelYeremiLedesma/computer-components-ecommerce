package com.aledesma.app.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.aledesma.app.models.entity.Category;

public interface ICategoryRepository extends CrudRepository<Category, Long>{

	@Query ("select c.name from Category c")
	 List<String> findAllCategoryNames();
}
