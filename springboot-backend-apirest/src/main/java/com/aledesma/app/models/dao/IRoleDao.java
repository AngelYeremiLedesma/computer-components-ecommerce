package com.aledesma.app.models.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.aledesma.app.models.entity.Role;

public interface IRoleDao extends CrudRepository<Role, Long>{
	
	public Optional<Role> findByName(String name);
	
}
