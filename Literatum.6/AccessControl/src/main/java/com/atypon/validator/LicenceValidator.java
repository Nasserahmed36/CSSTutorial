package com.atypon.validator;

import com.atypon.domain.ContentLicence;
import com.atypon.domain.Licence;
import com.atypon.domain.UserLicence;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class LicenceValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ContentLicence.class.isAssignableFrom(aClass) ||
                UserLicence.class.isAssignableFrom(aClass) ||
                Licence.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (o instanceof ContentLicence) {
            validateContentLicence((ContentLicence) o, errors);
        } else if (o instanceof UserLicence) {
            validateUserLicence((UserLicence) o, errors);
        } else {
            throw new RuntimeException("Not allowed object type:" + o.getClass());
        }
    }


    private void validateUserLicence(UserLicence licence, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username",
                "username.required");
    }

    private void validateContentLicence(ContentLicence licence, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contentId",
                "contentId.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price",
                "price.required");
    }

    private void validateLicence(Licence licence, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "licenceId",
                "licenceId.required");
    }

}
