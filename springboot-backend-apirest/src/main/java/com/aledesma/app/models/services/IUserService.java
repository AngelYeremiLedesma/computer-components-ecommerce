package com.aledesma.app.models.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.aledesma.app.dtos.CreateUserDto;
import com.aledesma.app.models.entity.UserEntity;

public interface IUserService {
	public ResponseEntity<?> save(CreateUserDto createUserDto);
}
