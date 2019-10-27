package com.suzumiya.dao;

import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPool;

@Repository("redisDao")
public class RedisDao {
    private JedisPool jedisPool;

    public String get(String key) {
        return jedisPool.getResource().get(key);
    }

    public String set(String key, String value) {
        return jedisPool.getResource().set(key, value);
    }

    public long del(String key) {
        return jedisPool.getResource().del(key);
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
