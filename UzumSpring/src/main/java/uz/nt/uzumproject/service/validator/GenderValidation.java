package uz.nt.uzumproject.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidation implements ConstraintValidator<IsValidGender, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        return s.equals("male") || s.equals("female");
    }
}
