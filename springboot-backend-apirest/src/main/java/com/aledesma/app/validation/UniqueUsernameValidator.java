package com.aledesma.app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.aledesma.app.models.repositories.IUserRepository;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsernameValidation, String> {
	
	@Autowired
	private IUserRepository userRepository;
	
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        if(userRepository.findByUsername(username).isPresent()) {
        	return false; 
        }
        return true;
    }
}