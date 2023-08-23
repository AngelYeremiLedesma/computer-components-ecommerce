package com.aledesma.app.models.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aledesma.app.dtos.CreateUserDto;
import com.aledesma.app.exceptions.RoleNotFoundException;
import com.aledesma.app.models.entity.Cart;
import com.aledesma.app.models.entity.ConfirmationToken;
import com.aledesma.app.models.entity.Customer;
import com.aledesma.app.models.entity.Role;
import com.aledesma.app.models.entity.UserEntity;
import com.aledesma.app.models.repositories.ICartRepository;
import com.aledesma.app.models.repositories.ICustomerRepository;
import com.aledesma.app.models.repositories.IRoleRepository;
import com.aledesma.app.models.repositories.IUserRepository;

import jakarta.mail.MessagingException;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public UserEntity generateUser(CreateUserDto createUserDto,Set<Role> roles,Customer customer) {
		
		UserEntity userEntity = UserEntity.builder().username(createUserDto.getUsername())
				.email(createUserDto.getEmail()).password(passwordEncoder.encode(createUserDto.getPassword()))
				.roleList(roles).customer(customer).enabled(false).build();
		
		return userEntity;
		
	}
	
    public void enableUser(String email) {
        userRepository.enableUser(email);
    }


}
