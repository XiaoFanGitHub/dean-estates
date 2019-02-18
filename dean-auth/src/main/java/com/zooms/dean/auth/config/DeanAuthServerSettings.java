package com.zooms.dean.auth.config;

import com.zooms.dean.auth.common.DeanAuthSecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author slacrey
 * @since 2018/1/20
 */
@Configuration
@ConfigurationProperties(prefix = "dean.auth.security")
public class DeanAuthServerSettings extends DeanAuthSecurityProperties {


}
