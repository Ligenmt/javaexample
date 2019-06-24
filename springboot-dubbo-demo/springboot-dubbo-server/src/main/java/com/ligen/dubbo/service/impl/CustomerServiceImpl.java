package com.ligen.dubbo.service.impl;

import com.ligen.dubbo.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {


    @Override
    public void hello() {
        System.out.println("hello");
    }
}
