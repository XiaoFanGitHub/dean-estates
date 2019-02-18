package com.zooms.dean.common.mybatis;

import java.lang.annotation.*;

/**
 * project：dean-cloud
 *
 * @author linfeng @ nondo
 * @date 2018/4/19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Table {
    String value();
}
