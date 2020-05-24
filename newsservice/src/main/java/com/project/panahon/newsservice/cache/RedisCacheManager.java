package com.project.panahon.newsservice.cache;

import com.project.panahon.newsservice.config.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Cache Manager Strategy for Redis.
 *
 * @author christian
 * @since 2020-05-09
 */
@Component
public class RedisCacheManager {

    private ServiceConfig serviceConfig;

    @SuppressWarnings("rawtypes")
    private RedisTemplate redisTemplate;

    @SuppressWarnings("rawtypes")
    @Autowired
    public RedisCacheManager(RedisTemplate redisTemplate,
                             ServiceConfig serviceConfig) {
        this.redisTemplate = redisTemplate;
        this.serviceConfig = serviceConfig;
    }

    /**
     * Redis Manager function to put an entry to the cache.
     *
     * @param type   {@link String}
     * @param key    {@link String}
     * @param object {@link Object}
     */
    @SuppressWarnings("unchecked")
    public void putCache(String type, String key, Object object) {
        redisTemplate.opsForHash().put(type, key, object);
    }

    /**
     * Redis Manager function to retrieve data in the cache.
     *
     * @param type  {@link String}
     * @param key   {@link String}
     * @param clazz {@link Class}
     * @param <T>   {@link T}
     * @return {@link T}
     */
    @SuppressWarnings("unchecked")
    public <T> T obtainCache(String type, String key, Class<T> clazz) {
        return clazz.cast(redisTemplate.opsForHash().get(type, key));
    }

    /**
     * Redis manager function to remove cache
     *
     * @param type {@link String}
     * @param key  {@link String}
     */
    @SuppressWarnings("unchecked")
    public void removeCache(String type, String key) {
        redisTemplate.opsForHash().delete(type, key);
    }

    /**
     * Redis Manager function to retrieve cache value using <br>
     * key.
     *
     * @param key {@link String}
     * @return {@link Object}
     */
    public Object obtainCacheValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Redis Manager function to set cache.
     *
     * @param key    {@link String}
     * @param object {@link Object}
     */
    @SuppressWarnings("unchecked")
    public void setCache(String key, Object object) {
        redisTemplate.opsForValue().set(key, object);
    }

    /**
     * Redis Manager function use for setting a expire <br>
     * duration of a certain cache by providing the cache <br>
     * key.
     *
     * @param key {@link String}
     */
    @SuppressWarnings("unchecked")
    public void obtainExpire(String key) {
        redisTemplate.expire(key, serviceConfig.getExpireDuration(), TimeUnit.MINUTES);
    }
}
