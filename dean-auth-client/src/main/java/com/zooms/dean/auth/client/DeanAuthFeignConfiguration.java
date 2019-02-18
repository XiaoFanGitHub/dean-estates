package com.zooms.dean.auth.client;

import com.zooms.dean.auth.common.TokenPrincipalProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

/**
 * @author slacrey
 * @since 2018/1/20
 */
@Configuration
@EnableFeignClients
@EnableEurekaClient
@Order(10)
@PropertySource(value = {"classpath:dean-auth-com.zooms.dean.auth.client.client.yml", "classpath:application.yml"})
public class DeanAuthFeignConfiguration {

    @Bean
    @ConditionalOnMissingClass
    DeanAuthClientFallbackFactory deanAuthClientFallbackFactory() {
        return new DeanAuthClientFallbackFactory();
    }

    @Bean
    @ConditionalOnMissingClass
    TokenPrincipalProvider tokenPrincipalProvider() {
        return new TokenPrincipalProvider();
    }

}
