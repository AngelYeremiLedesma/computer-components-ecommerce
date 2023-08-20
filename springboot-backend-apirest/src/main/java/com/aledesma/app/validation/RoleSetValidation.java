package com.aledesma.app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleSetValidator.class)
@Documented
public @interface RoleSetValidation {
    String message() default "Invalid roles";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
