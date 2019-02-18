package com.zooms.dean.auth.provider.redis;

import com.zooms.dean.auth.config.DeanAuthSendSettings;
import com.zooms.dean.auth.provider.VerificationCodeStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author nondo
 */
@Component
public class RedisVerificationCodeStore implements VerificationCodeStore {

    private static final String CODE_KEY = "phone_verification_code_key_";
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private DeanAuthSendSettings deanAuthSendSettings;

    @Override
    public void storeVerificationCode(String phone, String code) {

        redisTemplate.opsForSet().add(CODE_KEY + phone, code);
        redisTemplate.expire(CODE_KEY + phone, deanAuthSendSettings.getCodeExpTime(), TimeUnit.MINUTES);
    }

    @Override
    public Set<String> findVerificationCode(String phone) {

        return redisTemplate.opsForSet().members(CODE_KEY + phone);
    }

}
