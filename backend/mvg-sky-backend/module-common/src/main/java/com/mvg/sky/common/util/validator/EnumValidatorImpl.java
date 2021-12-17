package com.mvg.sky.common.util.validator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, Enum<?>[]> {
    private List<String> acceptedValues;
    private boolean allowNull;

    @Override
    public void initialize(EnumValidator annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
            .map(Enum::name)
            .collect(Collectors.toList());
        allowNull = annotation.allowNull();
    }

    @Override
    public boolean isValid(Enum<?>[] values, ConstraintValidatorContext context) {
        if(allowNull && values == null) {
            return true;
        }

        for(Enum<?> v : values) {
            if(!acceptedValues.contains(v.name())) {
                return false;
            }
        }

        return true;
    }
}
