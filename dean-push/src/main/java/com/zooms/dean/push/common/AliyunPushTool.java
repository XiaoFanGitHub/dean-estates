package com.zooms.dean.push.common;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20160801.*;
import com.zooms.dean.push.config.AliyunPushConfig;
import com.zooms.dean.push.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class AliyunPushTool {

    private final static Logger LOG = LoggerFactory.getLogger(AliyunPushTool.class);
    protected static final String region = "cn-hangzhou";

    private String accessKeyId;
    private String accessSecret;

    private long andriodAppKey;
    private long iosAppKey;

    private String apnsEnv;

    protected static DefaultAcsClient client;

    @Autowired
    public AliyunPushTool(AliyunPushConfig aliyunPushConfig) {

        this.accessKeyId = aliyunPushConfig.getAccess_key_id();
        this.accessSecret = aliyunPushConfig.getAccess_key_secret();

        this.andriodAppKey = aliyunPushConfig.getAndriodAppKey();
        this.iosAppKey = aliyunPushConfig.getIosAppKey();

        this.apnsEnv = aliyunPushConfig.getApnsEnv();


        client = getDefaultAcsClient();
    }

    private DefaultAcsClient getDefaultAcsClient() {

        if (client == null) {
            IClientProfile profile = DefaultProfile.getProfile(region, accessKeyId, accessSecret);
            client = new DefaultAcsClient(profile);
        }
        return client;

    }

    /**
     * 推送消息给android
     * <p>
     * 参见文档 https://help.aliyun.com/document_detail/48085.html
     */
    public void pushMessageToAndroid(Notification notification) throws Exception {

        PushMessageToAndroidRequest androidRequest = new PushMessageToAndroidRequest();
        // 安全性比较高的内容建议使用HTTPS
        androidRequest.setProtocol(ProtocolType.HTTPS);
        // 内容较大的请求，使用POST请求
        androidRequest.setMethod(MethodType.POST);
        androidRequest.setAppKey(andriodAppKey);
        if (notification.getDeviceId() == null) {
            if ("DEV".equals(apnsEnv)) {
                return;
            }
            androidRequest.setTarget("ALL");
            androidRequest.setTargetValue("ALL");
        } else {
            androidRequest.setTarget("DEVICE");
            androidRequest.setTargetValue(notification.getDeviceId());
        }
        androidRequest.setTitle(notification.getTitle());
        androidRequest.setBody(notification.getBody());
        PushMessageToAndroidResponse pushMessageToAndroidResponse = getDefaultAcsClient()
                .getAcsResponse(androidRequest);

        LOG.info("RequestId: %s, MessageId: %s\n",
                pushMessageToAndroidResponse.getRequestId(),
                pushMessageToAndroidResponse.getMessageId());

    }

    /**
     * 推送通知给android
     * <p>
     * 参见文档 https://help.aliyun.com/document_detail/48087.html
     */
    public void pushNoticeToAndroid(Notification notification) throws Exception {

        PushNoticeToAndroidRequest androidRequest = new PushNoticeToAndroidRequest();
        // 安全性比较高的内容建议使用HTTPS
        androidRequest.setProtocol(ProtocolType.HTTPS);
        // 内容较大的请求，使用POST请求
        androidRequest.setMethod(MethodType.POST);
        androidRequest.setAppKey(andriodAppKey);
        if (notification.getDeviceId() == null) {
            if ("DEV".equals(apnsEnv)) {
                return;
            }
            androidRequest.setTarget("ALL");
            androidRequest.setTargetValue("ALL");
        } else {
            androidRequest.setTarget("DEVICE");
            androidRequest.setTargetValue(notification.getDeviceId());
        }
        androidRequest.setTitle(notification.getTitle());
        androidRequest.setBody(notification.getBody());
        androidRequest.setExtParameters(notification.getExtParameters());

        PushNoticeToAndroidResponse pushNoticeToAndroidResponse = getDefaultAcsClient()
                .getAcsResponse(androidRequest);

        LOG.info("RequestId: %s, MessageId: %s\n",
                pushNoticeToAndroidResponse.getRequestId(),
                pushNoticeToAndroidResponse.getMessageId());

    }

    /**
     * 推送消息给iOS
     * <p>
     * 参见文档 https://help.aliyun.com/document_detail/48086.html
     */
    public void pushMessageToIOS(Notification notification) throws Exception {

        PushMessageToiOSRequest iOSRequest = new PushMessageToiOSRequest();
        // 安全性比较高的内容建议使用HTTPS
        iOSRequest.setProtocol(ProtocolType.HTTPS);
        // 内容较大的请求，使用POST请求
        iOSRequest.setMethod(MethodType.POST);
        iOSRequest.setAppKey(iosAppKey);
        if (notification.getDeviceId() == null) {
            if ("DEV".equals(apnsEnv)) {
                return;
            }
            iOSRequest.setTarget("ALL");
            iOSRequest.setTargetValue("ALL");
        } else {
            iOSRequest.setTarget("DEVICE");
            iOSRequest.setTargetValue(notification.getDeviceId());
        }
        iOSRequest.setTitle(notification.getTitle());
        iOSRequest.setBody(notification.getBody());

        PushMessageToiOSResponse pushMessageToiOSResponse = getDefaultAcsClient()
                .getAcsResponse(iOSRequest);

        LOG.info("RequestId: %s, MessageId: %s\n",
                pushMessageToiOSResponse.getRequestId(),
                pushMessageToiOSResponse.getMessageId());
    }

    /**
     * 推送通知给iOS
     * <p>
     * 参见文档 https://help.aliyun.com/document_detail/48088.html
     */
    public void pushNoticeToIOS(Notification notification) throws Exception {

        PushNoticeToiOSRequest iOSRequest = new PushNoticeToiOSRequest();
        // 安全性比较高的内容建议使用HTTPS
        iOSRequest.setProtocol(ProtocolType.HTTPS);
        // 内容较大的请求，使用POST请求
        iOSRequest.setMethod(MethodType.POST);
        iOSRequest.setAppKey(iosAppKey);
        // iOS的通知是通过APNS中心来发送的，需要填写对应的环境信息. DEV :表示开发环境, PRODUCT: 表示生产环境
        iOSRequest.setApnsEnv(apnsEnv);
        if (notification.getDeviceId() == null) {
            if ("DEV".equals(apnsEnv)) {
                return;
            }
            iOSRequest.setTarget("ALL");
            iOSRequest.setTargetValue("ALL");
        } else {
            iOSRequest.setTarget("DEVICE");
            iOSRequest.setTargetValue(notification.getDeviceId());
        }
        iOSRequest.setTitle(notification.getTitle());
        iOSRequest.setBody(notification.getBody());
        iOSRequest.setExtParameters(notification.getExtParameters());

        PushNoticeToiOSResponse pushNoticeToiOSResponse = getDefaultAcsClient()
                .getAcsResponse(iOSRequest);
        LOG.info("RequestId: %s, MessageId: %s\n",
                pushNoticeToiOSResponse.getRequestId(),
                pushNoticeToiOSResponse.getMessageId());
    }

    /**
     * 取消定时推送
     * <p>
     * 参见文档 https://help.aliyun.com/knowledge_detail/48090.html
     */
    public void cancelPush() throws Exception {
        CancelPushRequest request = new CancelPushRequest();
        request.setAppKey(iosAppKey);
        request.setMessageId("510456");
        CancelPushResponse response = getDefaultAcsClient().getAcsResponse(request);
        System.out.println(response.getRequestId());
    }

}
