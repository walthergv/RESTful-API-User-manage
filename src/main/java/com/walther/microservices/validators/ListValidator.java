package com.walther.microservices.validators;

import jakarta.validation.*;

import java.util.List;

public class ListValidator implements ConstraintValidator<ValidList, List> {
    @Override
    public boolean isValid(List list, ConstraintValidatorContext context) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        return list.stream()
                .allMatch(item -> validator.validate(item).isEmpty());
    }
}