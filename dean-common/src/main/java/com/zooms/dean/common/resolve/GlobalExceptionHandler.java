package com.zooms.dean.common.resolve;


import com.zooms.dean.common.DeanCommonAutoConfiguration;
import com.zooms.dean.common.exceptions.DeanAuthException;
import com.zooms.dean.common.exceptions.NotFoundThirdAccountException;
import com.zooms.dean.common.web.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理.
 *
 * @author lc
 * @date 2017年6月26日
 */
@RestControllerAdvice(basePackageClasses = {DeanCommonAutoConfiguration.class})
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public View<Void> exceptionResponse(HttpMessageNotReadableException exception, HttpServletResponse response) {

        logger.error("全局异常:{}", exception.getMessage(), exception);

        return View.ofError("Required request body is missing");
    }

    @ExceptionHandler(DeanAuthException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public View<Void> exceptionResponse(DeanAuthException exception, HttpServletResponse response) {

        logger.error("全局异常:{}", exception.getMessage(), exception);

        return View.of401Error("没有权限");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public View<Void> methodArgumentNotValid(MethodArgumentNotValidException exception, HttpServletResponse response) {

        logger.error("全局异常:{}", exception.getMessage(), exception);

        return View.ofParamsError(exception.getMessage());
    }
    
    @ExceptionHandler(NotFoundThirdAccountException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public View<Void> noBindThirdAccount(NotFoundThirdAccountException exception, HttpServletResponse response) {

        logger.error("全局异常:{}", exception.getMessage(), exception);

        return View.ofNoBindError(exception.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public View<Void> exceptionResponse(Exception exception, HttpServletResponse response) {

        logger.error("全局异常:{}", exception.getMessage(), exception);

        return View.ofError(exception.getMessage());
    }

}
