package com.itheima.case2.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

public class JedisUtil {
    private static JedisPool jedisPool;
    static{
        //1. 创建连接池
        ResourceBundle bundle = ResourceBundle.getBundle("jedis");
        String host = bundle.getString("host");
        int maxTotal = Integer.parseInt(bundle.getString("maxTotal"));
        int maxWaitMillis = Integer.parseInt(bundle.getString("maxWaitMillis"));
        int port = Integer.parseInt(bundle.getString("port"));

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);//配置连接池最大连接数
        config.setMaxWaitMillis(maxWaitMillis);//设置最长等待时间

        jedisPool = new JedisPool(config, host, port);
    }
    public static Jedis getResource(){
        //2. 从连接池中取出连接
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }
}
