package com.atypon.web.controller;

import com.atypon.domain.Identity;
import com.atypon.service.IdentityService;
import com.atypon.web.form.IdentityForm;
import com.atypon.web.validator.IdentityVaildator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class IdentityController implements Controller {
    private final IdentityService identityService;
    private final IdentityVaildator validator;

    public IdentityController(ServletContext context) {
        identityService = (IdentityService) context.getAttribute("identityService");
        validator = new IdentityVaildator();
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        IdentityForm form = extractIdentityForm(request);
        String operation = extractOperation(request);
        switch (operation) {
            case "all":
                return handleAll(request, response);
            case "delete":
                return handleDelete(request, response, form);
            case "userProfile":
                return handleUserProfile(request, response);
            case "create":
                return handleCreate(request, response, form);
            case "find":
                return handleFind(request, response, form);
            case "update":
                return handleUpdate(request,response,form);
        }
        return null;
    }


    private String handleAll(HttpServletRequest request, HttpServletResponse response) {
        if (setAsBadRequestIFBadMethod(request, response, "GET")) {
            return null;
        }
        request.setAttribute("identities", identityService.getAll());
        return "identities.jsp";
    }

    private String handleDelete(HttpServletRequest request,
                                HttpServletResponse response,
                                IdentityForm form) {
        request.setAttribute("identity", form);
        if (setAsBadRequestIFBadMethod(request, response, "GET")) {
            return null;
        }
        if (validator.isEmpty(form.getUsername())) {
            request.setAttribute("result", "Username is required");
            return "userProfile.jsp";
        }
        request.setAttribute("result",
                identityService.delete(form.getUsername()) ?
                        "User Deleted" : "User Cannot be Deleted");
        return "userProfile.jsp";
    }


    private String handleUserProfile(HttpServletRequest request,
                                     HttpServletResponse response) {
        if (setAsBadRequestIFBadMethod(request, response, "GET")) {
            return null;
        }
        return "userProfile.jsp";
    }


    private String handleCreate(HttpServletRequest request,
                                HttpServletResponse response,
                                IdentityForm form) {
        request.setAttribute("identity", form);
        if (setAsBadRequestIFBadMethod(request, response, "POST")) {
            return null;
        }
        List<String> errors = validator.validate(form);
        if (errors.isEmpty()) {
            Identity identity = extractIdentity(form);
            request.setAttribute("result",
                    identityService.create(identity) ?
                            "User Created" : "User Cannot be Created");
        } else {
            request.setAttribute("errors", errors);
        }
        return "userProfile.jsp";

    }

    private String handleFind(HttpServletRequest request,
                              HttpServletResponse response,
                              IdentityForm form) {
        if (setAsBadRequestIFBadMethod(request, response, "GET")) {
            return null;
        }
        if (validator.isEmpty(form.getUsername())) {
            request.setAttribute("result", "Username is required");
        }
        Identity identity = identityService.get(form.getUsername());
        request.setAttribute("identity", identity);
        request.setAttribute("result",
                identity != null ?
                        "User Found" : "User Not Found");
        return "userProfile.jsp";
    }


    private String handleUpdate(HttpServletRequest request,
                                HttpServletResponse response,
                                IdentityForm form) {
        request.setAttribute("identity", form);
        if (setAsBadRequestIFBadMethod(request, response, "POST")) {
            return null;
        }
        List<String> errors = validator.validate(form);
        if (errors.isEmpty()) {
            Identity identity = extractIdentity(form);
            request.setAttribute("result",
                    identityService.update(identity) ?
                            "User Updated" : "User Cannot be Updated");
        } else {
            request.setAttribute("errors", errors);
        }
        return "userProfile.jsp";

    }


    private IdentityForm extractIdentityForm(HttpServletRequest request) {
        IdentityForm form = new IdentityForm();
        form.setUsername(request.getParameter("username"));
        form.setLastName(request.getParameter("lastName"));
        form.setFirstName(request.getParameter("firstName"));
        form.setEmail(request.getParameter("email"));
        form.setPassword(request.getParameter("password"));
        form.setType(request.getParameter("type"));
        return form;
    }


    private Identity extractIdentity(IdentityForm form) {
        Identity identity = new Identity();
        identity.setUsername(form.getUsername().trim());
        identity.setLastName(form.getLastName().trim());
        identity.setFirstName(form.getFirstName().trim());
        identity.setEmail(form.getEmail().trim());
        identity.setPassword(form.getPassword().trim());
        identity.setType(form.getType().trim());
        return identity;
    }

    private String extractOperation(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String res[] = uri.split("/");
        if (res.length >= 4)
            return res[3];
        else
            return "all";
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
