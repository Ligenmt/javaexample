package com.ligen.distributedlock.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * 最简单版本，给锁加一个过期时间，避免服务重启或异常导致无法释放
 * Created by ligen on 2019/3/13.
 */
@Slf4j
public class RedisLockV1 {

    static final long EXPIRE_TIME = 10;

    @Autowired
    StringRedisTemplate redisTemplate;

    public boolean tryLock(String key) {
        return tryLock(key, null);
    }

    /**
     * 缺陷：如果在执行完第一条命令后异常或重启，锁将无法过期
     * 改进：使用lua脚本同时包含2条命令，但若是redis执行一条命令后异常或主从切换，仍会导致锁无法过期
     */
    public boolean tryLock(String key, String requestId) {

        ValueOperations<String, String> vo = redisTemplate.opsForValue();
        if (vo.setIfAbsent(key, "1")) {
            redisTemplate.expire(key, EXPIRE_TIME, TimeUnit.SECONDS);
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
