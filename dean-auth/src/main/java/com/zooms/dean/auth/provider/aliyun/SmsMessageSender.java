package com.zooms.dean.auth.provider.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zooms.dean.auth.exceptions.SendLimitException;
import com.zooms.dean.auth.provider.MessageSender;
import com.zooms.dean.common.config.AliyunSettings;
import com.zooms.dean.common.tool.JsonUtils;
import com.zooms.dean.common.web.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SmsMessageSender implements MessageSender {

    private final static Logger LOG = LoggerFactory.getLogger(SmsMessageSender.class);

    private static IAcsClient acsClient;

    private final AliyunSettings aliyunSettings;

    @Autowired
    public SmsMessageSender(AliyunSettings aliyunSettings) {

        this.aliyunSettings = aliyunSettings;
    }

    private IAcsClient getAcsClient() {
        if (acsClient == null) {
            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliyunSettings.getAccessKeyId(), aliyunSettings.getAccessKeySecret());
            try {
                DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", aliyunSettings.getSms().getProduct(), aliyunSettings.getSms().getDomain());
            } catch (ClientException e) {
                LOG.error(e.getMessage(), e);
            }
            acsClient = new DefaultAcsClient(profile);
        }
        return acsClient;
    }

    private View<String> sendSms(String phone, Map<String, Object> param, String outId, String templateCode) {
        //可自助调整超时时间
        System.setProperty("sun.net.com.zooms.dean.auth.client.client.defaultConnectTimeout", aliyunSettings.getSms().getDefaultConnectTimeout());
        System.setProperty("sun.net.com.zooms.dean.auth.client.client.defaultReadTimeout", aliyunSettings.getSms().getDefaultReadTimeout());

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(aliyunSettings.getSms().getSignName());
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        JsonUtils json = JsonUtils.getInstance();
        request.setTemplateParam(json.toJson(param));

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId(outId);

        try {

            SendSmsResponse sendSmsResponse = getAcsClient().getAcsResponse(request);
            return View.ofOk(sendSmsResponse.getMessage());
        } catch (ClientException e) {

            LOG.error(e.getMessage(), e);
            return View.ofError(e.getErrMsg());
        }

    }

    @Override
    public View<String> sendMessage(String phone, Map<String, Object> params, String templateCode) throws SendLimitException {
        return this.sendSms(phone, params, null, templateCode);
    }

}
