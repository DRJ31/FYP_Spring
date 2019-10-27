package com.suzumiya.dao;

import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Repository("redisDao")
public class RedisDao {
    private JedisPool jedisPool;

    public String get(String key) {
        return jedisPool.getResource().get(key);
    }

    public String set(String key, String value) {
        return jedisPool.getResource().set(key, value);
    }

    public long expire(String key, int expire) {
        return jedisPool.getResource().expire(key, expire);
    }

    public Set<String> keys(String pattern) {
        return jedisPool.getResource().keys(pattern);
    }

    public long del(String key) {
        return jedisPool.getResource().del(key);
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
