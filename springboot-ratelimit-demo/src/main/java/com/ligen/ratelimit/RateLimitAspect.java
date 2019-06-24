package com.ligen.ratelimit;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ligen on 2019/3/15.
 */
@Aspect
@Component
public class RateLimitAspect {

    @Autowired
    TokenBucket tokenBucket;

//    @annotation(com.ligen.javalab.ratelimit.RateLimit)
    @Before("execution(* com.ligen.javalab.ratelimit.controller.*.*(..))")
    public void before(){

        boolean b = tokenBucket.tryRate();
        System.out.println("方法执行前执行....." + b);
        if (!b) {
            throw new RuntimeException("限流");
        }

    }
}
