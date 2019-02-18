package com.zooms.dean.common.tool;

import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

/**
 * 验证错误处理类
 * 处理spring validate的错误结果
 * todo：增加一些没有定义错误的时候，展示字段
 *
 * @author yagnqing
 * @since 2017/9/15
 */
public class ValidateUtil {
    /**
     * 获取错误列表
     *
     * @param errors
     * @return
     */
    public static List list(List<ObjectError> errors) {
        List errorList = new ArrayList();
        for (ObjectError error : errors) {
            errorList.add(error.getDefaultMessage());
        }
        return errorList;
    }

    /**
     * 获取第一条错误
     *
     * @param errors
     * @return
     */
    public static String first(List<ObjectError> errors) {
        List errorList = list(errors);
        String error = null;
        if (errorList.size() > 0) {
            error = (String) errorList.get(0);
        }
        return error;
    }
}
