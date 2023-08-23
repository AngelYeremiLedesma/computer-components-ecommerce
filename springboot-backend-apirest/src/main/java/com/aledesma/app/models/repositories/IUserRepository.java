package com.aledesma.app.models.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.aledesma.app.models.entity.UserEntity;

import jakarta.transaction.Transactional;

public interface IUserRepository extends CrudRepository<UserEntity,Long>{
	
	public Optional<UserEntity> findByUsername(String username);
	
    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.enabled = TRUE WHERE u.email = ?1")
    int enableUser(String email);

}
