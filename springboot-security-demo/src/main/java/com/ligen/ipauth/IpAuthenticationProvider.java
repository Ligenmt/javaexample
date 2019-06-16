package com.ligen.ipauth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IpAuthenticationProvider implements AuthenticationProvider {

    final static Map<String, SimpleGrantedAuthority> ipAuthorityMap = new ConcurrentHashMap<>();
    //维护一个ip白名单列表，每个ip对应一定的权限
    static {
        ipAuthorityMap.put("127.0.0.1", new SimpleGrantedAuthority("ROLE_ADMIN"));
        ipAuthorityMap.put("10.236.69.103", new SimpleGrantedAuthority("ROLE_ADMIN"));
        ipAuthorityMap.put("0:0:0:0:0:0:0:1", new SimpleGrantedAuthority("ROLE_USER"));
        ipAuthorityMap.put("10.236.69.104", new SimpleGrantedAuthority("ROLE_ADMIN"));
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        IpAuthenticationToken ipAuthenticationToken = (IpAuthenticationToken) authentication;
        String ip = ipAuthenticationToken.getIp();
        SimpleGrantedAuthority simpleGrantedAuthority = ipAuthorityMap.get(ip);
        //不在ip白名单中
        if (simpleGrantedAuthority == null) {
            return null;
        }
        return new IpAuthenticationToken(ip, Arrays.asList(simpleGrantedAuthority));
    }
    //IpAuthenticationToken才能进来
    @Override
    public boolean supports(Class<?> authentication) {
        return (IpAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
