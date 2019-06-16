package com.ligen.util;


import com.ligen.model.AdminUser;

public class UserContext {
    private static final ThreadLocal<AdminUser> userInfoLocal = new ThreadLocal<>();

    public static void setUserInfo(AdminUser userInfo) {
        userInfoLocal.set(userInfo);
    }

    public static void clean() {
        userInfoLocal.remove();
    }

    public static AdminUser getUserInfo() {
        return userInfoLocal.get();
    }

}