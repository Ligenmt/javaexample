package com.ligen.ratelimit;

/**
 * 漏桶
 * Created by ligen on 2019/3/15.
 */
public class LeakyBucket implements RateLimiter  {
    @Override
    public boolean tryRate() {
        return false;
    }
}
