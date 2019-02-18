package com.zooms.dean.auth.common.mybatis;


import com.zooms.dean.auth.common.TokenPrincipalProvider;
import com.zooms.dean.common.annotation.CreatedBy;
import com.zooms.dean.common.annotation.CreatedDate;
import com.zooms.dean.common.annotation.LastModifiedBy;
import com.zooms.dean.common.annotation.LastModifiedDate;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@Component
public class CommonAuditingInterceptor implements Interceptor {

    private final TokenPrincipalProvider tokenPrincipalProvider;

    @Autowired
    public CommonAuditingInterceptor(TokenPrincipalProvider tokenPrincipalProvider) {
        this.tokenPrincipalProvider = tokenPrincipalProvider;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {


        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];

        boolean isMap = false;
        Object result = null;
        if (parameter instanceof Map) {
            result = ((Map) parameter).get("record");
            isMap = true;
        } else {
            result = parameter;
        }

        Field[] superFields = result.getClass().getSuperclass().getDeclaredFields();
        List<Field> fieldList = new ArrayList<>(Arrays.asList(superFields));

        Field[] fields = result.getClass().getDeclaredFields();
        fieldList.addAll(Arrays.asList(fields));


        Date currentDate = new Date();
        if (SqlCommandType.UPDATE == sqlCommandType) {
            for (Field field : fieldList) {
                if (tokenPrincipalProvider.getTokenPrincipal() != null) {
                    if (AnnotationUtils.getAnnotation(field, LastModifiedBy.class) != null) {
                        field.setAccessible(true);
                        field.set(result, tokenPrincipalProvider.getTokenPrincipal().getUserId());
                        field.setAccessible(false);
                    }
                }
                if (AnnotationUtils.getAnnotation(field, LastModifiedDate.class) != null) {
                    field.setAccessible(true);
                    field.set(result, currentDate);
                    field.setAccessible(false);
                }
            }
        } else if (SqlCommandType.INSERT == sqlCommandType) {
            for (Field field : fieldList) {
                if (tokenPrincipalProvider.getTokenPrincipal() != null) {
                    if (AnnotationUtils.getAnnotation(field, CreatedBy.class) != null) {
                        field.setAccessible(true);
                        field.set(result, tokenPrincipalProvider.getTokenPrincipal().getUserId());
                        field.setAccessible(false);
                    }
                    if (AnnotationUtils.getAnnotation(field, LastModifiedBy.class) != null) {
                        field.setAccessible(true);
                        field.set(result, tokenPrincipalProvider.getTokenPrincipal().getUserId());
                        field.setAccessible(false);
                    }
                }

                if (AnnotationUtils.getAnnotation(field, CreatedDate.class) != null) {
                    field.setAccessible(true);
                    field.set(result, currentDate);
                    field.setAccessible(false);
                }
                if (AnnotationUtils.getAnnotation(field, LastModifiedDate.class) != null) {
                    field.setAccessible(true);
                    field.set(result, currentDate);
                    field.setAccessible(false);
                }
            }
        }

        if (isMap){
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (HashMap<String, Object>) parameter;
            map.put("record", result);
            map.put("param1", result);
            invocation.getArgs()[1] = parameter;
        } else {
            invocation.getArgs()[1] = result;
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

}