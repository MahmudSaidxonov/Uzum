package uz.nt.uzumproject.service.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = GenderValidation.class)
public @interface IsValidGender {
    public String message() default "Invalid gender, namuna: male, female";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
