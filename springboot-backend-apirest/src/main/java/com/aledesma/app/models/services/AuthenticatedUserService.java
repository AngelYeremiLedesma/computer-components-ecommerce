package com.aledesma.app.models.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.aledesma.app.models.entity.UserEntity;
import com.aledesma.app.models.repositories.IUserRepository;

@Service
public class AuthenticatedUserService {

	@Autowired
	private IUserRepository userRepository;

	public boolean hasCustomerId(Long id) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepository.findByUsername(username).orElse(null);
		System.out.println(id);
		System.out.println(user.getCustomer().getId());
		if(user.getCustomer().getId().equals(id)) {
			return true;
		}
		return false;
	}

}