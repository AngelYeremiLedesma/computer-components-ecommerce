package com.aledesma.app.models.services;

import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aledesma.app.models.entity.UserEntity;
import com.aledesma.app.models.repositories.IUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User : " + username + " not found."));
		
		Collection<? extends GrantedAuthority> authorities = userEntity.getRoleList().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName())))
				.collect(Collectors.toSet());
		
		return new User(userEntity.getUsername(),userEntity.getPassword(),true,true,true,true,authorities);
	}

}
