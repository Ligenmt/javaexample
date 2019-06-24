package com.ligen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 限流的三种实现：计数器，漏桶，令牌桶
 * Created by ligen on 2019/3/15.
 */
@RestController
@RequestMapping("ratelimit")
public class TestController {

    @RequestMapping("test")
    public String test() {
        return "ok";
    }
}
