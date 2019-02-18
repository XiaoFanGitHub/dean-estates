package com.zooms.dean.auth.client;


import com.zooms.dean.auth.common.DeanAuthSecurityProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author slacrey
 * @since 2018/1/20
 */
@ConfigurationProperties(prefix = "dean.auth.security", ignoreUnknownFields = false)
@ConditionalOnProperty(prefix = "dean.auth.security", name = {"server", "authPath", "skipPath"}, matchIfMissing = false)
@PropertySource(value = {"classpath:dean-auth-com.zooms.dean.auth.client.client.yml", "classpath:application.yml"})
public class DeanAuthClientProperties extends DeanAuthSecurityProperties {

    public String server;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

}
