package com.aledesma.app.validation;

import com.aledesma.app.models.dao.IUserDao;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsernameValidation, String> {
	
	@Autowired
	private IUserDao userDao;
	
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        if(userDao.findByUsername(username).isPresent()) {
        	return false; 
        }
        return true;
    }
}