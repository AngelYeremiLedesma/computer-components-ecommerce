package com.aledesma.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aledesma.app.dtos.CreateUserDto;
import com.aledesma.app.models.services.IRegistrationService;
import com.aledesma.app.models.services.IUserService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path="/sign-up")
public class UserRestController {
	
	@Autowired
	IRegistrationService registrationService;
	
	@PostMapping(path = "")
	public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDto createUserDto, BindingResult bindingResult) throws MessagingException{
		
		Map<String, Object> response = new HashMap<>();
		
		if(bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getFieldErrors().stream()
				.map(error -> "The field ".concat(error.getField()).concat(" ").concat(error.getDefaultMessage()))
				.collect(Collectors.toList());
			
			response.put("errors", errors);
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		return registrationService.registerNewUser(createUserDto);
	}
	
    @GetMapping(path = "/confirm-email")
    public ResponseEntity<?> confirmUserEmail(@RequestParam String token) {
        return registrationService.confirmEmail(token);
    }
}
