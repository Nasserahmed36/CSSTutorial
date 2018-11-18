package com.atypon.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Controller {
    String handle(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException;
}
