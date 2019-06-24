package com.ligen.dubbo.service.impl;

import com.ligen.dubbo.model.User;
import com.ligen.dubbo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User findUser() {
        User user = new User();
        user.setName("a");
        return user;
    }
}
