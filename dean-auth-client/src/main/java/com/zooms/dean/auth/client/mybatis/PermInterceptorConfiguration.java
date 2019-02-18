package com.zooms.dean.auth.client.mybatis;

import com.zooms.dean.auth.client.DeanAuthClientAutoConfiguration;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 权限mybatis拦截器配置
 *
 * @author linfeng
 */
@Configuration
@MapperScan(basePackageClasses = DeanAuthClientAutoConfiguration.class)
public class PermInterceptorConfiguration {

    @Autowired
    private AuditingInterceptor auditingInterceptor;

    @Autowired
    private DataPermInterceptor dataPermInterceptor;

    @Bean
    public Interceptor[] getInterceptor() {
        return new Interceptor[]{dataPermInterceptor, auditingInterceptor};
    }

}
