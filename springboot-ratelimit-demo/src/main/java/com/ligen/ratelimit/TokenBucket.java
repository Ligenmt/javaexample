package com.ligen.ratelimit;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Component;

/**令牌桶
 * Created by ligen on 2019/3/15.
 */
@Component
public class TokenBucket {


    static RateLimiter rateLimiter;

    static {
        rateLimiter = RateLimiter.create(1);
    }

    public boolean tryRate() {
        boolean b = rateLimiter.tryAcquire(1);
        return b;
    }

}
