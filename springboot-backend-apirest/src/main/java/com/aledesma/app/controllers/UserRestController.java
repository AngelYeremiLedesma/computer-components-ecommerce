package com.aledesma.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aledesma.app.dtos.CreateUserDto;
import com.aledesma.app.models.services.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="/users")
public class UserRestController {

	@Autowired
	private IUserService userService;
	
	@PostMapping(path = "/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDto createUserDto, BindingResult bindingResult){
		
		Map<String, Object> response = new HashMap<>();
		
		if(bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getFieldErrors().stream()
				.map(error -> "The field ".concat(error.getField()).concat(" ").concat(error.getDefaultMessage()))
				.collect(Collectors.toList());
			
			response.put("errors", errors);
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		return userService.save(createUserDto);
	}
}
