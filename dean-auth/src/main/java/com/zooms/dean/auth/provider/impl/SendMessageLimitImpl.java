package com.zooms.dean.auth.provider.impl;

import com.zooms.dean.auth.config.DeanAuthSendSettings;
import com.zooms.dean.auth.exceptions.SendLimitException;
import com.zooms.dean.auth.provider.SendMessageLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author slacrey
 * @since 2018/1/17
 */
@Service
public class SendMessageLimitImpl implements SendMessageLimit {


    private final DeanAuthSendSettings deanAuthSendSettings;
    private final ValueOperations<String, String> longValueOperations;

    @Autowired
    public SendMessageLimitImpl(DeanAuthSendSettings deanAuthSendSettings,
                                SendMessageLimitRedisTemplate sendMessageLimitRedisTemplate) {

        this.deanAuthSendSettings = deanAuthSendSettings;
        this.longValueOperations = sendMessageLimitRedisTemplate.opsForValue();
    }

    @Override
    public void filter(String address) throws SendLimitException {

        //发送时间间隔
        int interval = deanAuthSendSettings.getSendLimit().getInterval();
        //限制时间
        int limitTime = deanAuthSendSettings.getSendLimit().getLimitTime();
        //在限制时间内的限制次数
        int limitCount = deanAuthSendSettings.getSendLimit().getLimitCount();

        // 发送频率限制
        String intervalFilterKey = "filter_send_sms_address_interval_" + address;
        String timeValue = longValueOperations.get(intervalFilterKey);
        if (timeValue != null) {
            throw new SendLimitException("验证码发送频率太快，请稍后重试");
        }
        longValueOperations.set(intervalFilterKey, "1", interval, TimeUnit.SECONDS);

        // 一定时间发送总数限制
        String countFilterKey = "filter_send_sms_address_count_" + address;
        String countValue = longValueOperations.get(countFilterKey);
        if (countValue != null) {
            if (Long.valueOf(countValue) > limitCount) {
                throw new SendLimitException("验证码发送超过次数限制，请稍后重试");
            }
            longValueOperations.increment(countFilterKey, 1L);
        } else {
            longValueOperations.set(countFilterKey, "1", limitTime, TimeUnit.SECONDS);
        }

    }

}
