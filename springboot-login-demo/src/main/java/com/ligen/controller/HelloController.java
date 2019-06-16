package com.ligen.controller;

import com.ligen.config.LoginRequired;
import com.ligen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by ligen on 2018/5/8.
 */
@Controller
public class HelloController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(HttpServletResponse response, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        userService.login(response, username, password);
        return "index";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @LoginRequired
    public void logoutPost(HttpServletResponse response) throws Exception {
        userService.logout(response);
        response.sendRedirect("/");
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/hello")
    @LoginRequired
    public String hello() {
        return "hello";
    }

    @RequestMapping("/hello2")
    @LoginRequired
    public String hello2() {
        return "hello";
    }

}
