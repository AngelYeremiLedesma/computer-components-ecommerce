package com.aledesma.app.models.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.aledesma.app.models.entity.UserEntity;
import com.aledesma.app.models.repositories.IUserRepository;

@Service
public class AuthenticatedUserService {

	@Autowired
	private IUserRepository userRepository;

	public boolean hasCustomerIdOrIsAdmin(Long id) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User Not Found"));
		if(isAdmin()){
			return true;
		}
		if(user.getCustomer().getId().equals(id)) {
			return true;
		}

		return false;
	}

	public boolean isAdmin() {
		List<SimpleGrantedAuthority> roles = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for(SimpleGrantedAuthority role: roles){
			if(role.getAuthority().equals("ROLE_ADMIN")){
				return true;
			}
		}
		return false;
	}

	public boolean permitAll(){
		return true;
	}

}