package com.atypon.web.validator;

import com.atypon.domain.Identity;
import com.atypon.web.form.IdentityForm;

import java.util.ArrayList;
import java.util.List;

public class IdentityVaildator extends Validator<IdentityForm> {
    @Override
    public List<String> validate(IdentityForm identity) {
        List<String> errors = new ArrayList<>();
        if(isEmpty(identity.getUsername())) {
            errors.add("Username is required");
        }
        if(isEmpty(identity.getLastName())) {
            errors.add("Last name is required");
        }
        if(isEmpty(identity.getFirstName())) {
            errors.add("First name is required");
        }
        if(isEmpty(identity.getEmail())) {
            errors.add("Email is required");
        }
        if(isEmpty(identity.getPassword())) {
            errors.add("Password is required");
        }
        if(isEmpty(identity.getType())) {
            errors.add("User type is required");
        }
        return errors;
    }
}
