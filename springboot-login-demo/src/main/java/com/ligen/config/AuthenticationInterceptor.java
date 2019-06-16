package com.ligen.config;


import com.ligen.exception.AuthenticationException;
import com.ligen.mapper.AuthenticationMapper;
import com.ligen.model.AdminUser;
import com.ligen.response.CodeMsg;
import com.ligen.service.CacheService;
import com.ligen.util.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AuthenticationMapper authenticationMapper;
    @Autowired
    CacheService cacheService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
//        LoginRequired typeAnnotation = method.getDeclaringClass().getAnnotation(LoginRequired.class);
        if (methodAnnotation != null) {
            String nameToken = cacheService.getAdminUserCookieValue(request);
            if (StringUtils.isEmpty(nameToken)) {
                throw new AuthenticationException();
            }

            AdminUser adminUser = cacheService.getAdminUserByToken(nameToken);
            if (adminUser == null) {
                throw new AuthenticationException();
            }
            int hasRole = authenticationMapper.checkRole(adminUser.getId(), request.getRequestURI());
            if (hasRole == 0) {
                throw new AuthenticationException(CodeMsg.无权限);
            }

            UserContext.setUserInfo(adminUser);
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        UserContext.clean();
    }


}