package com.zooms.dean.auth.provider;

import com.zooms.dean.auth.exceptions.SendLimitException;
import com.zooms.dean.common.web.View;

import java.util.Map;

/**
 * @author linfeng
 */
public interface MessageSender {

    /**
     * 发送消息，包括邮件和短信
     * @param address 发送地址，可以是邮件或手机号
     * @param params 发送参数
     * @param templateCode 模板
     * @return 发送消息
     * @throws SendLimitException 发送限制异常
     */
    View<String> sendMessage(String address, Map<String, Object> params, String templateCode) throws SendLimitException;

}
