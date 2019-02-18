package com.zooms.dean.auth.config;

import com.zooms.dean.auth.provider.token.JwtToken;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JwtSettings
 *
 * @author linfeng
 * @since 2017/7/30
 */
@Configuration
@ConfigurationProperties(prefix = "dean.auth.jwt")
public class DeanAuthJwtSettings {
    /**
     * {@link JwtToken} will expire after this time.
     */
    private Integer accessTokenExpTime;

    /**
     * Token issuer.
     */
    private String tokenIssuer;

    /**
     * Key is used to sign {@link JwtToken}.
     */
    private String tokenSigningKey;

    /**
     * {@link JwtToken} can be refreshed during this timeframe.
     */
    private Integer refreshTokenExpTime;

    public Integer getRefreshTokenExpTime() {
        return refreshTokenExpTime;
    }

    public void setRefreshTokenExpTime(Integer refreshTokenExpTime) {
        this.refreshTokenExpTime = refreshTokenExpTime;
    }

    public Integer getAccessTokenExpTime() {
        return accessTokenExpTime;
    }

    public void setAccessTokenExpTime(Integer accessTokenExpTime) {
        this.accessTokenExpTime = accessTokenExpTime;
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }

    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }

    public String getTokenSigningKey() {
        return tokenSigningKey;
    }

    public void setTokenSigningKey(String tokenSigningKey) {
        this.tokenSigningKey = tokenSigningKey;
    }
}
