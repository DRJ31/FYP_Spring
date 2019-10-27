package com.suzumiya.dao;

import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Repository("redisDao")
public class RedisDao {
    private JedisPool jedisPool;

    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get(key);
        jedis.close();
        return result;
    }

    public void set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, value);
        jedis.close();
    }

    public void expire(String key, int expire) {
        Jedis jedis = jedisPool.getResource();
        jedis.expire(key, expire);
        jedis.close();
    }

    public Set<String> keys(String pattern) {
        Jedis jedis = jedisPool.getResource();
        Set<String> results = jedis.keys(pattern);
        jedis.close();
        return results;
    }

    public void del(String key) {
        Jedis jedis = jedisPool.getResource();
        jedis.del(key);
        jedis.close();
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
