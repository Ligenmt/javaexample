package com.ligen.preauthorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //加这一行使得@PreAuthorize生效
@Profile("preauthorize")
public class PreAuthorizeSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UrlUserDetailService urlUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/basic/**").hasRole("BASIC")
                .antMatchers("/api/session").permitAll()
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers("/api/**").hasRole("BASIC");

        http.formLogin();
//        http.logout()
//                .logoutUrl("/api/session/logout")
//                // 登出前调用，可用于日志
//                .addLogoutHandler(customLogoutHandler)
//                // 登出后调用，用户信息已不存在
//                .logoutSuccessHandler(customLogoutHandler);
//        http.exceptionHandling()
//                // 已登入用户的权限错误
//                .accessDeniedHandler(customAccessDeniedHandler)
//                // 未登入用户的权限错误
//                .authenticationEntryPoint(customAccessDeniedHandler);
        http.csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(urlUserDetailsService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return (String) charSequence;
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return String.valueOf(charSequence).equals(s);
            }
        });
    }
}
