package com.zooms.dean.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "dean.aliyun")
@ConditionalOnProperty(prefix = "dean.aliyun", name = {"access_key_id", "access_key_secret", "sms", "oss"}, matchIfMissing = false)
@PropertySource(value = {"classpath:application.yml"}) //加载外部配置文件 用@value可以获取配置文件的值
public class AliyunSettings {

    private String accessKeyId;
    private String accessKeySecret;
    private SmsSettings sms;
    private OssSettings oss;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public SmsSettings getSms() {
        return sms;
    }

    public void setSms(SmsSettings sms) {
        this.sms = sms;
    }

    public OssSettings getOss() {
        return oss;
    }

    public void setOss(OssSettings oss) {
        this.oss = oss;
    }

    @ConfigurationProperties(prefix = "dean.aliyun.sms")
    public static class SmsSettings {

        private String product;
        private String domain;
        private String defaultConnectTimeout;
        private String defaultReadTimeout;
        private String signName;

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getDefaultConnectTimeout() {
            return defaultConnectTimeout;
        }

        public void setDefaultConnectTimeout(String defaultConnectTimeout) {
            this.defaultConnectTimeout = defaultConnectTimeout;
        }

        public String getDefaultReadTimeout() {
            return defaultReadTimeout;
        }

        public void setDefaultReadTimeout(String defaultReadTimeout) {
            this.defaultReadTimeout = defaultReadTimeout;
        }

        public String getSignName() {
            return signName;
        }

        public void setSignName(String signName) {
            this.signName = signName;
        }
    }

    @ConfigurationProperties(prefix = "dean.aliyun.oss")
    public static class OssSettings {

        private Boolean enable;
        private String endpoint;
        private String bucketName;
        private String bucketNameVideo;

        public Boolean getEnable() {
            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }

        public String getBucketNameVideo() {
            return bucketNameVideo;
        }

        public void setBucketNameVideo(String bucketNameVideo) {
            this.bucketNameVideo = bucketNameVideo;
        }
    }

}
