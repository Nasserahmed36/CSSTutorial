package com.atypon.web.validator;

import com.atypon.web.form.JournalForm;

import java.util.ArrayList;
import java.util.List;

public class JournalValidator extends Validator<JournalForm> {

    @Override
    public List<String> validate(JournalForm form) {
        List<String> errors = new ArrayList<>();
        if (isEmpty(form.getId())) {
            errors.add("Id is required");
        }
        if (isEmpty(form.getElectronicIssn())) {
            errors.add("Electronic ISSN is required");
        }
        if (isEmpty(form.getPrintIssn())) {
            errors.add("Print ISSN is required");
        }
        if (isEmpty(form.getTitle())) {
            errors.add("Title is required");
        }
        if (isEmpty(form.getPublisherName())) {
            errors.add("Publisher Name is required");
        }
        if (isEmpty(form.getPublisherLocation())) {
            errors.add("Publisher Location is required");
        }
        return errors;
    }
}
