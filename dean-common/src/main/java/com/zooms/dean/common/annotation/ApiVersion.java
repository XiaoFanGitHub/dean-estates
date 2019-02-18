package com.zooms.dean.common.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 自定义接口版本标注注解，该注解可标注在
 * 类及方法函数上，并且在运行时环境起作用，
 * 并且可以被潜入到javadoc中，同时指定该
 * 标签为web映射注解.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {
    int value() default 1;
}
