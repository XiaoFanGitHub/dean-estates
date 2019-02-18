package com.zooms.dean.auth.config;

import com.zooms.dean.auth.config.intercaptor.DeanAuthHandlerInterceptor;
import com.zooms.dean.common.DeanCommonWebMvcConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * 权限web配置
 *
 * @author linfeng
 */
@Configuration
public class DeanAuthWebMvcConfiguration extends DeanCommonWebMvcConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(DeanAuthWebMvcConfiguration.class);

    private final DeanAuthServerSettings deanAuthServerSettings;

    private final DeanAuthHandlerInterceptor deanAuthHandlerInterceptor;

    @Autowired
    public DeanAuthWebMvcConfiguration(DeanAuthServerSettings deanAuthServerSettings, DeanAuthHandlerInterceptor deanAuthHandlerInterceptor) {
        this.deanAuthServerSettings = deanAuthServerSettings;
        this.deanAuthHandlerInterceptor = deanAuthHandlerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] authPaths = deanAuthServerSettings.getAuthPath();
        String[] skipPaths = deanAuthServerSettings.getSkipPath();
        if (authPaths == null) {
            LOG.warn("dean.auth.security.auth_path is not configured default is /**");
        }
        if (skipPaths == null) {
            LOG.warn("dean.auth.security.skip_path is not configured");
        }
        InterceptorRegistration registration = registry.addInterceptor(deanAuthHandlerInterceptor);
        if (authPaths != null) {
            registration.addPathPatterns(authPaths);
        } else {
            registration.addPathPatterns("/**");
        }
        if (skipPaths != null) {
            registration.excludePathPatterns(skipPaths);// 排除拦截路径配置
        }
    }
}
