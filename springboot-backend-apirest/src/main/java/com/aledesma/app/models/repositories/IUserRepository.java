package com.aledesma.app.models.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.aledesma.app.models.entity.UserEntity;

public interface IUserRepository extends CrudRepository<UserEntity,Long>{
	
	public Optional<UserEntity> findByUsername(String username);

}
