package com.zooms.dean.auth.config;

import com.zooms.dean.auth.DeanAuthApplication;
import com.zooms.dean.common.resolve.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 * @author linfeng
 */
@RestControllerAdvice(basePackageClasses = {DeanAuthApplication.class})
public class DeanAuthExceptionHandler extends GlobalExceptionHandler {

}
