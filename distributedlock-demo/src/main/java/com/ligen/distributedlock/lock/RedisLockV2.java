package com.ligen.distributedlock.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * SetNx解决两条命令无法保证原子性的问题。
 * Created by ligen on 2019/3/13.
 */
@Slf4j
public class RedisLockV2 {

    static final long EXPIRE_TIME = 10;

    @Autowired
    StringRedisTemplate redisTemplate;

    public boolean tryLock(String key) {
        return tryLock(key, null);
    }

    /**
     * 缺陷：某客户端C1获取到锁后如果时间过长，在锁失效前没释放，另一客户端C2会再次获取锁，相当于两个客户端在
     * 同时执行。如果C1先完毕，会释放C2的锁。
     */
    public boolean tryLock(String key, String requestId) {
        ValueOperations<String, String> vo = redisTemplate.opsForValue();
        if (vo.setIfAbsent(key, "1", EXPIRE_TIME, TimeUnit.SECONDS)) {
            log.info("lock {}", key);
            return true;
        } else {
            return false;
        }
    }

    public void releaseLock(String key, String requestId) {
        log.info("release lock {}", key);
        redisTemplate.delete(key);
    }
}
