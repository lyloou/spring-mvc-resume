package com.lyloou.practice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        return new RedisTemplate<>();
    }
}
