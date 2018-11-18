package com.atypon.validator;


import com.atypon.domain.Identity;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class IdentityValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Identity.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username",
                "username.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "password.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName",
                "firstName.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
                "lastName.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
                "email.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type",
                "type.required");

    }

}
