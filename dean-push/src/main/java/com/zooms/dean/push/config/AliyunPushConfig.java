package com.zooms.dean.push.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云push配置
 */
@Component
@ConfigurationProperties(prefix = "dean.aliyun.push")
public class AliyunPushConfig {
    private String access_key_id;
    private String access_key_secret;
    private long andriodAppKey;
    private long iosAppKey;
    private String apnsEnv;

    public String getAccess_key_id() {
        return access_key_id;
    }

    public void setAccess_key_id(String access_key_id) {
        this.access_key_id = access_key_id;
    }

    public String getAccess_key_secret() {
        return access_key_secret;
    }

    public void setAccess_key_secret(String access_key_secret) {
        this.access_key_secret = access_key_secret;
    }

	public long getAndriodAppKey() {
		return andriodAppKey;
	}

	public void setAndriodAppKey(long andriodAppKey) {
		this.andriodAppKey = andriodAppKey;
	}

	public long getIosAppKey() {
		return iosAppKey;
	}

	public void setIosAppKey(long iosAppKey) {
		this.iosAppKey = iosAppKey;
	}

	public String getApnsEnv() {
		return apnsEnv;
	}

	public void setApnsEnv(String apnsEnv) {
		this.apnsEnv = apnsEnv;
	}

}
