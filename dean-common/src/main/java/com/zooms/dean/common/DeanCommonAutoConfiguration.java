package com.zooms.dean.common;

import com.zooms.dean.common.config.AliyunSettings;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author linfeng
 */
@Configuration
@EnableConfigurationProperties(AliyunSettings.class)//开启使用映射实体对象
@ComponentScan(basePackageClasses = DeanCommonAutoConfiguration.class)
public class DeanCommonAutoConfiguration {


}
