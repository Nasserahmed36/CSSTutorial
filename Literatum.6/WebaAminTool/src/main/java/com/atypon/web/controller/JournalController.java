package com.atypon.web.controller;

import com.atypon.domain.Journal;
import com.atypon.service.JournalService;
import com.atypon.web.form.JournalForm;
import com.atypon.web.validator.JournalValidator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JournalController implements Controller {

    private final JournalService journalService;
    private final JournalValidator validator;

    public JournalController(ServletContext context) {
        journalService = (JournalService) context.getAttribute("journalService");
        this.validator = new JournalValidator();
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String operation = extractOperation(request);
        switch (operation) {
            case "form":
                return handleForm(request, response);
            case "create":
                return handleCreate(request, response);
            case "update":
                return handleUpdate(request, response);
            case "find":
                return handleFind(request, response);
            case "delete":
                return handleDelete(request, response);
        }
        return null;
    }


    private String extractOperation(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String res[] = uri.split("/");
        if (res.length >= 4)
            return res[3];
        else
            return "form";
    }


    private String handleForm(HttpServletRequest request,
                              HttpServletResponse response) {
        if (setAsBadRequestIFBadMethod(request, response, "GET")) {
            return null;
        }
        return "journal.jsp";
    }

    private String handleCreate(HttpServletRequest request,
                                HttpServletResponse response) {
        JournalForm form = extractJournalForm(request);
        request.setAttribute("form", form);
        List<String> errors = validator.validate(form);
        if (setAsBadRequestIFBadMethod(request, response, "POST")) {
            return null;
        }
        if (errors.isEmpty()) {
            Journal journal = extractJournal(form);
            request.setAttribute("result",
                    journalService.create(journal) ?
                            "Journal Created" : "Journal Cannot be Created");

        } else {
            request.setAttribute("errors", errors);
        }
        return "journal.jsp";
    }


    private String handleUpdate(HttpServletRequest request,
                                HttpServletResponse response) {
        JournalForm form = extractJournalForm(request);
        request.setAttribute("form", form);
        List<String> errors = validator.validate(form);
        if (setAsBadRequestIFBadMethod(request, response, "POST")) {
            return null;
        }
        if (errors.isEmpty()) {
            Journal journal = extractJournal(form);
            request.setAttribute("result",
                    journalService.update(journal) ?
                            "Journal Updated" : "Journal Cannot be Updated");

        } else {
            request.setAttribute("errors", errors);
        }
        return "journal.jsp";
    }

    private String handleFind(HttpServletRequest request,
                              HttpServletResponse response) {
        JournalForm form = extractJournalForm(request);
        request.setAttribute("form", form);
        if (setAsBadRequestIFBadMethod(request, response, "GET")) {
            return null;
        }
        if (validator.isEmpty(form.getPrintIssn())) {
            request.setAttribute("result", "Print ISSN is required");
        }
        Journal journal = journalService.get(form.getPrintIssn());
        request.setAttribute("form", journal);
        request.setAttribute("result",
                journal != null ?
                        "Journal Found" : "Journal Not Found");
        return "journal.jsp";
    }


    private String handleDelete(HttpServletRequest request,
                                HttpServletResponse response) {
        JournalForm form = extractJournalForm(request);
        request.setAttribute("form", form);
        if (setAsBadRequestIFBadMethod(request, response, "GET")) {
            return null;
        }
        if (validator.isEmpty(form.getPrintIssn())) {
            request.setAttribute("result", "PrintIssn is required");
        }
        request.setAttribute("result",
                journalService.delete(form.getPrintIssn()) ?
                        "Journal Deleted" : "Journal Cannot be Deleted");

        return "journal.jsp";
    }


    private Journal extractJournal(JournalForm form) {
        Journal journal = new Journal();
        journal.setPrintIssn(form.getPrintIssn());
        journal.setElectronicIssn(form.getElectronicIssn());
        journal.setId(form.getId());
        journal.setTitle(form.getTitle());
        journal.setPublisherName(form.getPublisherName());
        journal.setPublisherLocation(form.getPublisherLocation());
        return journal;
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

    private JournalForm extractJournalForm(HttpServletRequest request) {
        JournalForm form = new JournalForm();
        form.setId(request.getParameter("id").trim());
        form.setElectronicIssn(request.getParameter("eIssn").trim());
        form.setPrintIsnn(request.getParameter("pIssn").trim());
        form.setElectronicIssn(request.getParameter("eIssn").trim());
        form.setTitle(request.getParameter("title").trim());
        form.setPublisherName(request.getParameter("publisherName").trim());
        form.setPublisherLocation(request.getParameter("publisherLocation").trim());
        return form;
    }
}
