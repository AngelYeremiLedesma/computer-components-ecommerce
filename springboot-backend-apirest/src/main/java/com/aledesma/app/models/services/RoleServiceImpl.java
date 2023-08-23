package com.aledesma.app.models.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aledesma.app.exceptions.RoleNotFoundException;
import com.aledesma.app.models.entity.Role;
import com.aledesma.app.models.repositories.IRoleRepository;

@Service
public class RoleServiceImpl implements IRoleService{

	@Autowired
	IRoleRepository roleRepository;
	
	@Override
	public Set<Role> convertStrToRoles(Set<String> strRoles){
		Set<Role> roles = strRoles.stream().map((roleName) -> {
			Role role = roleRepository.findByName(roleName)
					.orElseThrow(() -> new RoleNotFoundException("No matching roles in the DB"));
			return role;
		}).collect(Collectors.toSet());
		
		return roles;
	}
	
	@Override
	public Boolean isNotAdmin(Set<Role> roles) {
		return roles.stream().noneMatch(role -> role.getName().equals("ADMIN"));
	}
}
