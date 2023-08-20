package com.aledesma.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aledesma.app.security.filters.JwtAuthenticationFilter;
import com.aledesma.app.security.filters.JwtAuthorizationFilter;
import com.aledesma.app.security.jwt.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired 
	JwtAuthorizationFilter jwtAuthorizationFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception{
		
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
		jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
		
		return httpSecurity
                .csrf(config -> config.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/users/register").permitAll();
                    auth.anyRequest().permitAll();//authenticated();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(HttpSecurity httpsecurity, PasswordEncoder passwordEncoder) throws Exception {
		return httpsecurity.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder)
				.and()
				.build();
	}
}