package com.zooms.dean.push.config;

import com.zooms.dean.auth.client.mybatis.PermInterceptorConfiguration;
import com.zooms.dean.push.PushApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration//开启配置
@MapperScan(basePackageClasses = PushApplication.class)
public class PushMybatisConfig extends PermInterceptorConfiguration {


}