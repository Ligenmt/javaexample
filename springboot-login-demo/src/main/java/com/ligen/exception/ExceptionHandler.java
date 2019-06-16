package com.ligen.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by ligen on 2017/8/5.
 */
@ControllerAdvice
public class ExceptionHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    public String handleBusinessException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
    public String handleSecurityException(Model model, HttpServletRequest request, HttpServletResponse response, Exception ex) {

        AuthenticationException e = (AuthenticationException) ex;
        int errcode = e.getErrcode();
        model.addAttribute("errcode", errcode);
        if (errcode == 403) {
            return "error";
        }
        try {
            response.sendRedirect("login");
        } catch (IOException ioe) {
            e.printStackTrace();
        }
        return "login";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public String handleException(Model model, HttpServletRequest request, HttpServletResponse response, Exception ex) {
        ex.printStackTrace();
        model.addAttribute("errcode", 500);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error";
    }
}
