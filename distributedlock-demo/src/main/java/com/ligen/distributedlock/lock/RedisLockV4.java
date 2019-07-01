package com.ligen.distributedlock.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 将value设为客户端唯一id，释放锁的时候检查，任何客户端都可以解锁
 * 单节点安全
 * 缺陷：
 * 在redis集群中，若master获取到锁未完成同步情况下crash，新的master依然可以获取锁，导致多个客户端带锁
 * 可用redlock算法改善
 * Created by ligen on 2019/3/13.
 */
@Slf4j
public class RedisLockV4 {

    private static final long EXPIRE_TIME = 10;

    private static final Long RELEASE_SUCCESS = 1L;

    @Autowired
    StringRedisTemplate redisTemplate;

    public boolean tryLock(String key, String requestId) {
        ValueOperations<String, String> vo = redisTemplate.opsForValue();
        if (vo.setIfAbsent(key, requestId, EXPIRE_TIME, TimeUnit.SECONDS)) {
            log.info("lock {}", key);
            return true;
        } else {
            return false;
        }
    }

    public void releaseLock(String key, String requestId) {

        /**
         * 缺陷：
         * C1加锁，一段时间之后C1解锁，在执行之前，锁突然过期了，此时C2尝试加锁成功，然后客户端A再执行del()方法，则将C2的锁给解除了。
         * 改善：采用lua脚本执行
         */
//        String value = redisTemplate.opsForValue().get(key);
//        if (requestId.equals(value)) {
//            log.info("releaseLock key:{}, id:{}", key, requestId);
//            redisTemplate.delete(key);
//        }

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), requestId);

        if (RELEASE_SUCCESS.equals(result)) {
            log.info("releaseLock key:{}, id:{}", key, requestId);
        } else {
            log.info("unsafe releaseLock key:{}, id:{}", key, requestId);
        }
    }
}
