package com.ligen.preauthorize;

import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("preauthorize")
public class LoginController {

//    @RequestMapping(value = "/login")
//    public Object login(@AuthenticationPrincipal User loginedUser, @RequestParam(name = "logout", required = false) String logout) {
//        if (logout != null) {
//            return "logout";
//        }
//        if (loginedUser != null) {
//            return loginedUser;
//        }
//        return "error";
//    }

    //@PreAuthorize("hasRole('ADMIN')") 等于 @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public Object admin() {
        return "admin";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Object user() {
        return "user";
    }

}
