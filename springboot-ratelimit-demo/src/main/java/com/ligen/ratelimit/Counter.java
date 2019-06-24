package com.ligen.ratelimit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**计数器
 * 缺陷：有峰刺效应
 * Created by ligen on 2019/3/15.
 */
public class Counter implements RateLimiter {

    @Autowired
    StringRedisTemplate redisTemplate;

    public boolean tryRate() {

        long time = System.currentTimeMillis();
        time = time / 1000L;
        String timeStr = time + "";
        ValueOperations<String, String> vo = redisTemplate.opsForValue();
        Boolean flag = vo.setIfAbsent(timeStr, "1", 1, TimeUnit.HOURS);
        if (!flag) {
            Long increment = redisTemplate.boundValueOps(timeStr).increment();
            if (increment >= 10L) {
                return false;
            }
        }
        return true;
    }

}
