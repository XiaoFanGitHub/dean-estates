package com.zooms.dean.auth.client;

import com.zooms.dean.auth.client.intercaptor.DeanAuthClientHandlerInterceptor;
import com.zooms.dean.auth.client.mybatis.AuditingInterceptor;
import com.zooms.dean.auth.client.mybatis.DataPermInterceptor;
import com.zooms.dean.auth.common.TokenPrincipalProvider;
import feign.Feign;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author slacrey
 * @since 2018/1/20
 */
@Configuration//开启配置
@EnableConfigurationProperties(DeanAuthClientProperties.class)//开启使用映射实体对象
@ConditionalOnClass(value = {Feign.class})
@Import(value = {DeanAuthFeignConfiguration.class, DeanWebInterceptorConfiguration.class})
@AutoConfigureAfter(value = {FeignAutoConfiguration.class, AopAutoConfiguration.class})
@ConditionalOnProperty(prefix = "dean.auth", name = "server", matchIfMissing = true)
@PropertySource(value = {"classpath:dean-auth-com.zooms.dean.auth.client.client.yml", "classpath:application.yml"})
public class DeanAuthClientAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(TokenPrincipalProvider.class)
    public TokenPrincipalProvider tokenPrincipalProvider() {

        return new TokenPrincipalProvider();
    }

    @Bean
    @ConditionalOnMissingBean(DeanAuthClientHandlerInterceptor.class)
    public DeanAuthClientHandlerInterceptor deanAuthHandlerInterceptor(@Autowired DeanAuthClientProperties deanAuthClientProperties,
                                                                       @Autowired DeanAuthClient deanAuthClient,
                                                                       @Autowired TokenPrincipalProvider tokenPrincipalProvider) {

        return new DeanAuthClientHandlerInterceptor(deanAuthClientProperties, deanAuthClient, tokenPrincipalProvider);
    }

    @Bean
    @ConditionalOnMissingBean(AuditingInterceptor.class)
    public AuditingInterceptor auditingInterceptor(@Autowired TokenPrincipalProvider tokenPrincipalProvider) {
        return new AuditingInterceptor(tokenPrincipalProvider);
    }

    @Bean
    @ConditionalOnMissingBean(DataPermInterceptor.class)
    public DataPermInterceptor dataPermissionsInterceptor(@Autowired TokenPrincipalProvider tokenPrincipalProvider) {
        return new DataPermInterceptor(tokenPrincipalProvider);
    }

    @Bean
    @ConditionalOnMissingBean(JsonConfig.class)
    public JsonConfig jsonConfig() {
        return new JsonConfig();
    }

    @Bean
    @ConditionalOnMissingBean(DataInitPostProcessor.class)
    public DataInitPostProcessor dataInitPostProcessor(@Autowired DeanAuthClient deanAuthClient) {
        return new DataInitPostProcessor(jsonConfig(), deanAuthClient);
    }

    @Bean
    @ConditionalOnMissingBean(RequestInterceptor.class)
    public RequestInterceptor headerInterceptor() {
        return template -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        String values = request.getHeader(name);
                        template.header(name, values);
                    }
                }
            }

        };
    }

}
