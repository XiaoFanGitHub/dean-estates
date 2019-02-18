package com.zooms.dean.push.config;

import com.zooms.dean.common.DeanCommonWebMvcConfiguration;
import com.zooms.dean.push.PushApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = PushApplication.class)
public class DeanPushWebMvcConfiguration extends DeanCommonWebMvcConfiguration {

}
