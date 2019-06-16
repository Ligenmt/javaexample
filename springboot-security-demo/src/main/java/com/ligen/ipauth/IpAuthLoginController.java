package com.ligen.ipauth;

import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Profile("ipauth")
public class IpAuthLoginController {

    @RequestMapping(value = "/ip_login", method = RequestMethod.GET)
    public String ipLogin() {
        return "ip_login";
    }
//    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @ResponseBody
    public Object user() {
        return "admin";
    }

//    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public Object hello() {
        return "hello";
    }

}
