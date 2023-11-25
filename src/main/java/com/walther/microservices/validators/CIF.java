package com.walther.microservices.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CIFValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CIF {
    String message() default "Invalid CIF number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
