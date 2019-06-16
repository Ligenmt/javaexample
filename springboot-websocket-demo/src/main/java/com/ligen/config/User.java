package com.ligen.config;

import java.security.Principal;

/**
 * Created by ligen on 2018/5/12.
 */
public class User implements Principal {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
