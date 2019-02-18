package com.zooms.dean.common.annotation;

import java.lang.annotation.*;

/**
 * project：dean-cloud
 *
 * @author linfeng @ nondo
 * @date 2018/5/9
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SensitiveKey {

}
