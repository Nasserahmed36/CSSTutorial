package com.atypon.web.controller;

import com.atypon.domain.ArticleSubmission;
import com.atypon.service.ArticleSubmissionService;
import com.atypon.web.form.ArticleSubmissionForm;
import com.atypon.web.validator.ArticleSubmissionValidator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

public class BackstageController implements Controller {
    private final ArticleSubmissionService articleSubmissionService;
    private final ArticleSubmissionValidator articleSubmissionValidator;

    public BackstageController(ServletContext context) {
        articleSubmissionService = (ArticleSubmissionService) context.getAttribute("articleSubmissionService");
        articleSubmissionValidator = new ArticleSubmissionValidator();
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String operation = extractOperation(request);
        switch (operation) {
            case "form":
                return handleForm(request, response);
            case "articleUpload":
                return handleArticleUpload(request, response);
        }
        return null;
    }


    private String handleForm(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("submissions", articleSubmissionService.getAll());
        if (setAsBadRequestIFBadMethod(request, response, "GET")) {
            return null;
        }
        return "backstage.jsp";
    }

    private String handleArticleUpload(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setAttribute("submissions", articleSubmissionService.getAll());
        if (setAsBadRequestIFBadMethod(request, response, "POST")) {
            return null;
        }
        ArticleSubmissionForm form = extractArticleSubmissionForm(request);
        request.setAttribute("form",form);
        List<String> errors = articleSubmissionValidator.validate(form);
        if (errors.isEmpty()) {
            ArticleSubmission articleSubmission = extractArticleSubmission(form);
            request.setAttribute("result",
                    articleSubmissionService.save(articleSubmission, form.getFilePart().getInputStream())
                            ? "Article Uploaded" : "Article Cannot be Uploaded");
        } else {
            request.setAttribute("errors", errors);
        }
        return "backstage.jsp";
    }

    private ArticleSubmissionForm extractArticleSubmissionForm(HttpServletRequest request)
            throws IOException, ServletException {
        ArticleSubmissionForm articleSubmissionForm = new ArticleSubmissionForm();
        articleSubmissionForm.setSeriesIssn(request.getParameter("seriesISSN"));
        Part part = request.getPart("articleFile");
        articleSubmissionForm.setFilePart(part);
        return articleSubmissionForm;
    }

    private ArticleSubmission extractArticleSubmission(ArticleSubmissionForm form) {
        ArticleSubmission articleSubmission = new ArticleSubmission();
        articleSubmission.setSeriesIssn(form.getSeriesIssn().trim());
        articleSubmission.setArticleFileName(form.getArticleFileName().trim());
        return articleSubmission;
    }


    private String extractOperation(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String res[] = uri.split("/");
        if (res.length >= 4)
            return res[3];
        else
            return "form";
    }

    private boolean setAsBadRequestIFBadMethod(HttpServletRequest request,
                                               HttpServletResponse response,
                                               String expectedMethod) {
        if (!request.getMethod().equals(expectedMethod)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return true;
        }
        return false;
    }
}
