package com.atypon.web.validator;

import com.atypon.web.form.ArticleSubmissionForm;

import java.util.ArrayList;
import java.util.List;

public class ArticleSubmissionValidator extends Validator<ArticleSubmissionForm> {
    public List<String> validate(ArticleSubmissionForm articleSubmissionForm) {
        List<String> errors = new ArrayList<>();
        String seriesIssn = articleSubmissionForm.getSeriesIssn();
        if (seriesIssn == null || seriesIssn.equals("")) {
            errors.add("Series ISSN is required");
        }
        String fileName = articleSubmissionForm.getArticleFileName();
        if (articleSubmissionForm.getFilePart() == null ||
                fileName == null || fileName.trim().isEmpty()) {
            errors.add("File is required");
        }
        return errors;
    }
}
