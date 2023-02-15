package uz.nt.uzumproject.service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PhoneNumberValidation.class)
public @interface IsValidPhoneNumber {
    public String message() default "Invalid phoneNumber, namuna: 935960107";

    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
