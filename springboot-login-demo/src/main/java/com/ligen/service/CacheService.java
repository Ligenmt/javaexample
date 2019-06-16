package com.ligen.service;

import com.alibaba.fastjson.JSON;
import com.ligen.model.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by ligen on 2018/5/9.
 */
@Service
public class CacheService {

    @Autowired
    StringRedisTemplate redisTemplate;

    public void addCookie(HttpServletResponse response, AdminUser user) {
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("adminuser_" + token, JSON.toJSONString(user), 7, TimeUnit.DAYS);
        Cookie cookie = new Cookie("name_token", token);
        cookie.setMaxAge(3600 * 24 * 7);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void removeCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("name_token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public AdminUser getAdminUserByToken(String token) {
        String str = redisTemplate.opsForValue().get("adminuser_" + token);
        AdminUser adminUser = null;
        if (!StringUtils.isEmpty(str)) {
            adminUser = JSON.parseObject(str, AdminUser.class);
        }
        return adminUser;
    }

    public String getAdminUserCookieValue(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if (name.equals("name_token")) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
