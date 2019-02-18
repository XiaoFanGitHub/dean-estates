package com.zooms.dean.auth.config;

import com.zooms.dean.auth.common.mybatis.CommonAuditingInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 权限mybatis配置
 *
 * @author linfeng
 */
@Configuration
@MapperScan(basePackages = "com.nondo.dean.auth.mapper")
public class DeanAuthMyBatisConfiguration {

    @Autowired
    private CommonAuditingInterceptor commonAuditingInterceptor;

    @Bean
    public Interceptor[] getInterceptor() {
        return new Interceptor[]{commonAuditingInterceptor};
    }

}
