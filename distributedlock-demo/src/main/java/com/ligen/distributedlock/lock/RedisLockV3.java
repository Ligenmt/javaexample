package com.ligen.distributedlock.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * 将value设置为过期时间，如果锁已经存在则获取锁的过期时间，和当前时间比较，如果锁已经过期，则设置新的过期时间，返回加锁成功。
 * Created by ligen on 2019/3/13.
 */
@Slf4j
public class RedisLockV3 {

    static final long EXPIRE_TIME = 10;

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 缺陷：
     * 客户端自己生成过期时间，需要强制要求分布式下每个客户端的时间必须同步。
     * 锁不具备拥有者标识，任何客户端都可以解锁。
     */
    public boolean tryLock(String key, String requestId) {
        ValueOperations<String, String> vo = redisTemplate.opsForValue();
        long now = System.currentTimeMillis();
        String expires = (now + EXPIRE_TIME * 1000L) + "";
        if (vo.setIfAbsent(key, expires)) {
            log.info("lock {}", key);
            return true;
        }
        // 如果锁存在，获取锁的过期时间
        String currentValue = vo.get(key);
        // 锁已过期，获取上一个锁的过期时间，并设置现在锁的过期时间
        if (currentValue != null && Long.parseLong(currentValue) < now) {
            String oldValue = vo.getAndSet(key, expires);
            /**
             * 考虑多线程并发的情况，只有一个线程的设置值和当前值相同，它才有权利加锁
             * 缺陷：当锁过期的时候，如果多个客户端同时执行jedis.getSet()方法，那么虽然最终只有一个客户端可以加锁，但是这个客户端的锁的过期时间可能被其他客户端覆盖。
             */
            if (oldValue != null && oldValue.equals(currentValue)) {
                return true;
            }
        }
        return false;
    }

    public void releaseLock(String key, String requestId) {
        log.info("release lock {}", key);
        redisTemplate.delete(key);
    }
}
