package com.zooms.dean.push.config;

import com.zooms.dean.common.resolve.GlobalExceptionHandler;
import com.zooms.dean.push.PushApplication;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {PushApplication.class})
public class PushExceptionHandler extends GlobalExceptionHandler {

}
