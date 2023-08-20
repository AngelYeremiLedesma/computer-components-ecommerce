package com.aledesma.app.validation;

import com.aledesma.app.models.entity.ERole;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleSetValidator implements ConstraintValidator<RoleSetValidation, Set<String>> {
    @Override
    public boolean isValid(Set<String> roles, ConstraintValidatorContext context) {
        Set<String> validRoles = Arrays.stream(ERole.values())
                .map(ERole::name)
                .collect(Collectors.toSet());
        
        for (String role : roles) {
            if (!validRoles.contains(role)) {
                return false;
            }
        }
        
        return true;
    }
}
