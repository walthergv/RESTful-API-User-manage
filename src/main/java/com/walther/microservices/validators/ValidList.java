package com.walther.microservices.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ListValidator.class)
public @interface ValidList {
    String message() default "Invalid list";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
