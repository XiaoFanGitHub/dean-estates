package com.zooms.dean.common.config.mapping;

import com.zooms.dean.common.annotation.ApiVersion;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * 首先，需要在Servlet配置中注册该类
 * 其次，动态编译时匹配ApiVersion标签，
 * 获取控制器中对应类或方法的版本值.
 */
public class ApiRequestMapping extends RequestMappingHandlerMapping {

    /**
     * 映射匹配自定义注解（ApiVersion标注在类级别）
     *
     * @param handlerType
     * @return
     */
    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {

        ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return createCondition(apiVersion);
    }

    /**
     * 映射匹配自定义注解（ApiVersion标注在方法级别）
     *
     * @param method
     * @return
     */
    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {

        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return createCondition(apiVersion);
    }

    /**
     * 获取当前控制器标注的版本，初始化Api版本条件
     *
     * @param apiVersion
     * @return
     */
    private RequestCondition<ApiVersionCondition> createCondition(ApiVersion apiVersion) {
        return apiVersion == null ? null : new ApiVersionCondition(apiVersion.value());
    }

}
