//package com.ligen.config;
//
//import com.alibaba.fastjson.JSONObject;
//import com.ligen.preauthorize.UrlUserDetailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) //加这一行使得@PreAuthorize生效
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    /**
//     * 自定义403返回值
//     * @return
//     */
//    @Bean
//    public AccessDeniedHandler getAccessDeniedHandler() {
//        return new AccessDeniedHandler() {
//            @Override
//            public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
//                int status = httpServletResponse.getStatus();
//                JSONObject json = new JSONObject();
//                json.put("code", status);
//                PrintWriter writer = httpServletResponse.getWriter();
//                writer.println(json.toJSONString());
//            }
//        };
//    }
//
//    @Bean
//    public AuthenticationEntryPoint authenticationEntryPoint() {
//        return new AuthenticationEntryPoint() {
//            @Override
//            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//                int status = httpServletResponse.getStatus();
//                if (status == 401) {
//                    httpServletResponse.getWriter().println(new JSONObject().fluentPut("code", status));
//                }
//            }
//        };
//    }
//
//    public AuthenticationFailureHandler authenticationFailureHandler() {
//        return new AuthenticationFailureHandler() {
//            @Override
//            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//                httpServletResponse.getWriter().println(new JSONObject().fluentPut("code", httpServletResponse.getStatus()));
//            }
//        };
//    }
//
//    public AuthenticationSuccessHandler authenticationSuccessHandler() {
//        return new AuthenticationSuccessHandler() {
//            @Override
//            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//                httpServletResponse.getWriter().println(new JSONObject().fluentPut("code", httpServletResponse.getStatus()));
//            }
//        };
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
////        http.exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());
////        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
////        http.formLogin().failureHandler(authenticationFailureHandler()).successHandler(authenticationSuccessHandler());
//        http.csrf().disable()
//            .authorizeRequests()
//            .antMatchers("/login").permitAll()
//            .antMatchers("/logout").permitAll()
//            .antMatchers("/images/**").permitAll()
//            .antMatchers("/js/**").permitAll()
//            .antMatchers("/css/**").permitAll()
//            .antMatchers("/fonts/**").permitAll()
//            .antMatchers("/").permitAll()
//            .anyRequest().authenticated()
//            .and()
//            .sessionManagement()
//            .and()
//            .logout()
//            .and()
//            .httpBasic();
////        DaoAuthenticationProvider
//    }
//
//    @Autowired
//    UrlUserDetailService urlUserDetailService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(urlUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
//        auth.userDetailsService(urlUserDetailService).passwordEncoder(new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence rawPassword) {
//                return (String) rawPassword;
//            }
//
//            @Override
//            public boolean matches(CharSequence rawPassword, String encodedPassword) {
//                return rawPassword.equals(encodedPassword);
//            }
//        });
//    }
//}
