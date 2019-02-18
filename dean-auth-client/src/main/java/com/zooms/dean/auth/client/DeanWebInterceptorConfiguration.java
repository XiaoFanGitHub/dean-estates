package com.zooms.dean.auth.client;

import com.zooms.dean.auth.client.intercaptor.DeanAuthClientHandlerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author linfeng
 */
@Configuration
@Order(999)
public class DeanWebInterceptorConfiguration extends WebMvcConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(DeanWebInterceptorConfiguration.class);

    @Autowired
    private DeanAuthClientProperties deanAuthClientProperties;
    @Autowired
    private DeanAuthClientHandlerInterceptor deanAuthClientHandlerInterceptor;

    /**
     * 拦截器配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] authPaths = deanAuthClientProperties.getAuthPath();
        String[] skipPaths = deanAuthClientProperties.getSkipPath();
        if (authPaths == null) {
            LOG.warn("dean.auth.security.auth_path is not configured default is /**");
        }
        if (skipPaths == null) {
            LOG.warn("dean.auth.security.skip_path is not configured");
        }
        InterceptorRegistration registration = registry.addInterceptor(deanAuthClientHandlerInterceptor);
        if (authPaths != null) {
            registration.addPathPatterns(authPaths);
        } else {
            registration.addPathPatterns("/**");
        }
        if (skipPaths != null) {
            /**
             *     excludePathPatterns方法是排除访问路径,但是当你排除的url路径在项目中并不存在的时候,springboot会将路径编程转发到/error,
             */
            registration.excludePathPatterns(skipPaths);
        }
    }

}
