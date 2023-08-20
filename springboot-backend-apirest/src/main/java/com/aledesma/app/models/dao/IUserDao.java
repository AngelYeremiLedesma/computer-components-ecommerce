package com.aledesma.app.models.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.aledesma.app.models.entity.UserEntity;

public interface IUserDao extends CrudRepository<UserEntity,Long>{
	
	public Optional<UserEntity> findByUsername(String username);

}
