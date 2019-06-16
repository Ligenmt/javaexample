package com.ligen.service;

import com.ligen.mapper.AuthenticationMapper;
import com.ligen.model.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by ligen on 2018/5/9.
 */
@Service
public class UserService {

    @Autowired
    AuthenticationMapper authenticationMapper;
    @Autowired
    CacheService cacheService;

    public void login(HttpServletResponse response, String name, String password) {
        AdminUser adminUser = authenticationMapper.selectUser(name);
        if (adminUser == null || !adminUser.getPassword().equals(password)) {

        }
        //生成cookie
        cacheService.addCookie(response, adminUser);
    }

    public void logout(HttpServletResponse response) {
        cacheService.removeCookie(response);
    }
}
