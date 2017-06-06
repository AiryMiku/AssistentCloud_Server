package com.kexie.acloud.config;

import com.kexie.acloud.util.MyJedisConnectionFactory;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by zojian on 2017/5/22.
 */


@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    // 连接池配置
    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        // 最大空闲连接数
        config.setMaxIdle(100);
        // 初始化连接数
        config.setMinIdle(50);
        // 最大等待时间
        config.setMaxWaitMillis(1000);
        // 最大连接数
        config.setMaxTotal(500);
        // borrow的时候执行validateObject检验
        config.setTestOnBorrow(true);
        return config;
    }

    @Bean
    public MyJedisConnectionFactory myJedisConnectionFactory(JedisPoolConfig config){
        MyJedisConnectionFactory factory = new MyJedisConnectionFactory(config);
        return factory;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(MyJedisConnectionFactory jedisConnectionFactory){
        return new StringRedisTemplate(jedisConnectionFactory);
    }
}
