package com.zooms.dean.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zooms.dean.auth.common.TokenPrincipalProvider;
import com.zooms.dean.auth.common.crypto.password.PasswordEncoder;
import com.zooms.dean.auth.common.crypto.password.StandardPasswordEncoder;
import com.zooms.dean.common.config.JsonObjectMapper;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 权限相关配置
 * @author linfeng
 */
@Configuration
@Order(10)
public class DeanAuthConfiguration {

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder("%twgdh%");
    }

    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean registration = new ServletRegistrationBean(
                dispatcherServlet);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return registration;
    }

    @Bean
    public TokenPrincipalProvider tokenPrincipalProvider() {
        return new TokenPrincipalProvider();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new JsonObjectMapper();
    }
}
