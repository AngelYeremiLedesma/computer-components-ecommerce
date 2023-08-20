package com.aledesma.app.models.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.aledesma.app.models.entity.Role;

public interface IRoleRepository extends CrudRepository<Role, Long>{
	
	public Optional<Role> findByName(String name);
	
}
