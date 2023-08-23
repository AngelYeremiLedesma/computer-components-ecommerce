package com.aledesma.app.models.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import com.aledesma.app.dtos.CreateUserDto;
import com.aledesma.app.models.entity.Customer;
import com.aledesma.app.models.entity.Role;
import com.aledesma.app.models.entity.UserEntity;

public interface IUserService {
	
	UserEntity generateUser(CreateUserDto createUserDto, Set<Role> roles, Customer customer);
	
    void enableUser(String email);

}
