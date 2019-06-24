package com.ligen.ratelimit;

/**
 * Created by ligen on 2019/3/18.
 */
public interface RateLimiter {

    boolean tryRate();
}
