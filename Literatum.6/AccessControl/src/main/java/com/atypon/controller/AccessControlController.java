package com.atypon.controller;

import com.atypon.domain.ContentLicence;
import com.atypon.domain.Identity;
import com.atypon.domain.UserLicence;
import com.atypon.service.IdentityService;
import com.atypon.service.LicenceService;
import com.atypon.validator.IdentityValidator;
import com.atypon.validator.LicenceValidator;
import com.sun.istack.internal.NotNull;
import com.sun.javaws.exceptions.ErrorCodeResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@RestController
public class AccessControlController {

    private Validator identityValidator = new IdentityValidator();
    private Validator licenceValidator = new LicenceValidator();

    @Resource(name = "errorsProperties")
    private Properties errorsProperties;

    @Autowired
    private IdentityService identityService;
    @Autowired
    private LicenceService licenceService;


    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public Object login(@RequestParam String username, @RequestParam String password,
                        HttpServletRequest request) {
        Identity identity = identityService.getIdentity(username, password);
        if (identity != null && isAdmin(identity)) {
            request.getSession().setAttribute("identity", identity);
            return identity;
        }
        return "Incorrect Admin username or password";
    }

    private boolean isAdmin(Identity identity) {
        return identity.getType().equals("super") || identity.getType().equals("admin");
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public Object logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "Logged out";
    }


    @RequestMapping(value = "/getIdentity", method = {RequestMethod.GET})
    public Object getIdentity(@RequestParam String username,
                              HttpServletResponse response) {
        if(StringUtils.isEmpty(username)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return errorsProperties.getProperty("username.required");
        }
        Identity identity = identityService.getIdentity(username);
        if(identity == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "User Not Found";
        }
        return identity;
    }

    @RequestMapping(value = "/getIdentities", method = {RequestMethod.GET})
    public Object getIdentities() {
        return identityService.getAll();
    }

    @RequestMapping(value = "/createIdentity", method = {RequestMethod.POST})
    public Object createIdentity(Identity identity, BindingResult bindingResult,
                                 HttpServletResponse response) {
        identityValidator.validate(identity, bindingResult);
        if (bindingResult.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return getErrors(bindingResult);
        }
        return identityService.createIdentity(identity);
    }

    @RequestMapping(value = "/updateIdentity", method = {RequestMethod.POST})
    public Object updateIdentity(Identity identity, BindingResult bindingResult,
                                 HttpServletResponse response) {
        identityValidator.validate(identity, bindingResult);
        if (bindingResult.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return getErrors(bindingResult);
        }
        return identityService.updateIdentity(identity);
    }

    @RequestMapping(value = "/deleteIdentity", method = {RequestMethod.DELETE})
    public Object deleteIdentity(@RequestParam String username) {
        return identityService.deleteIdentity(username);
    }


    @RequestMapping(value = "/getUserLicences", method = {RequestMethod.GET})
    public Object getUserLicences(@RequestParam String username,
                                  @RequestParam String contentId) {
        return licenceService.getLicences(username, contentId);
    }

    @RequestMapping(value = "/getContentLicences", method = {RequestMethod.GET})
    public Object getContentLicences(@RequestParam String contentId) {
        return licenceService.getLicences(contentId);
    }


    @RequestMapping(value = "/createUserLicence", method = {RequestMethod.POST})
    public Object createUserLicence(UserLicence userLicence,
                                    BindingResult bindingResult,
                                    HttpServletResponse response) {
        licenceValidator.validate(userLicence, bindingResult);
        if (bindingResult.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return getErrors(bindingResult);
        }
        return licenceService.createUserLicence(userLicence);
    }


    @RequestMapping(value = "/createContentLicence", method = {RequestMethod.POST})
    public Object createContentLicence(ContentLicence contentLicence, BindingResult bindingResult,
                                       HttpServletResponse response) {
        licenceValidator.validate(contentLicence, bindingResult);
        if (bindingResult.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return getErrors(bindingResult);
        }
        return licenceService.createContentLicence(contentLicence);
    }


    @RequestMapping(value = "/deleteContentLicence", method = {RequestMethod.DELETE})
    public Object deleteContentLicence(@RequestParam int contentLicenceId) {
        return licenceService.deleteContentLicence(contentLicenceId);
    }

    @RequestMapping(value = "/deleteUserLicence", method = {RequestMethod.DELETE})
    public Object deleteContentLicence(UserLicence userLicence,
                                       BindingResult bindingResult,
                                       HttpServletResponse response) {
        licenceValidator.validate(userLicence, bindingResult);
        if (bindingResult.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return getErrors(bindingResult);
        }
        return licenceService.deleteUserLicence(userLicence);
    }

    @RequestMapping(value = "/updateContentLicence", method = {RequestMethod.POST})
    public Object updateContentLicence(ContentLicence contentLicence,
                                       BindingResult bindingResult,
                                       HttpServletResponse response) {
        licenceValidator.validate(contentLicence, bindingResult);
        if (bindingResult.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return getErrors(bindingResult);
        }
        return licenceService.updateContentLicence(contentLicence);
    }

    @RequestMapping(value = "/updateUserLicenceDate", method = {RequestMethod.POST})
    public Object updateUserLicenceDate(UserLicence userLicence,
                                        BindingResult bindingResult,
                                        HttpServletResponse response) {
        licenceValidator.validate(userLicence, bindingResult);
        if (bindingResult.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return getErrors(bindingResult);
        }
        return licenceService.updateUserLicenceDate(userLicence);
    }


    @RequestMapping(value = "/hasAccess", method = {RequestMethod.GET})
    public Object hasAccess(@RequestParam String username, @RequestParam String contentId) {
        return licenceService.hasAccess(username, contentId);
    }


    private List<String> getErrors(BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.add(errorsProperties.getProperty(error.getCode()));
        }
        return errors;
    }
}
