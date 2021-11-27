package com.mvg.sky.account.util.validator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, Enum<?>[]> {
    private List<String> acceptedValues;

    @Override
    public void initialize(EnumValidator annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
            .map(Enum::name)
            .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(Enum<?>[] values, ConstraintValidatorContext context) {
        for(Enum<?> v : values) {
            if(!acceptedValues.contains(v.name())) {
                return false;
            }
        }
        return true;
    }
}
