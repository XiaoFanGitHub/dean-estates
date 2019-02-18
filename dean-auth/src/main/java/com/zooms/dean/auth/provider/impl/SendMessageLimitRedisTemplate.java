package com.zooms.dean.auth.provider.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class SendMessageLimitRedisTemplate extends RedisTemplate<String, String> {

    private final RedisConnectionFactory connectionFactory;

    @Autowired
    public SendMessageLimitRedisTemplate(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.setKeySerializer(new StringRedisSerializer());
        this.setValueSerializer(new StringRedisSerializer());
    }

    @Override
    public RedisConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

}