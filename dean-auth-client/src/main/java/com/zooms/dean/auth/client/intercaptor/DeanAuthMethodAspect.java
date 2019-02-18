package com.zooms.dean.auth.client.intercaptor;

import com.zooms.dean.auth.common.TokenPrincipal;
import com.zooms.dean.auth.common.TokenPrincipalProvider;
import com.zooms.dean.auth.common.annotation.HasPermission;
import com.zooms.dean.auth.common.annotation.HasRole;
import com.zooms.dean.common.exceptions.DeanAuthException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static com.zooms.dean.auth.common.TokenPrincipal.PARAMS_SPLIT;

/**
 * @author nondo
 */
@Aspect
@Component
public class DeanAuthMethodAspect {

    private final TokenPrincipalProvider principalStorage;

    public DeanAuthMethodAspect(TokenPrincipalProvider principalStorage) {
        this.principalStorage = principalStorage;
    }


    @Pointcut("@annotation(com.nondo.dean.auth.common.annotation.HasPermission)")
    public void hasPermission() {
    }

    @Pointcut("@annotation(com.nondo.dean.auth.common.annotation.HasUser)")
    public void hasUser() {
    }

    @Pointcut("@annotation(com.nondo.dean.auth.common.annotation.HasRole)")
    public void hasRole() {
    }

    @Around("hasPermission()")
    public Object isAccessMethodOfHasPermission(ProceedingJoinPoint joinPoint) throws Throwable {

        TokenPrincipal principal = principalStorage.getTokenPrincipal();
        if (principal == null) {
            throw new DeanAuthException("没有权限");
        }

        //获取访问目标方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        // 判断该方法是否加了@HasPermission 注解
        if (targetMethod.isAnnotationPresent(HasPermission.class)) {

            HasPermission hasPermission = targetMethod.getAnnotation(HasPermission.class);
            String[] permission = hasPermission.value();
            List<String> result = Arrays.asList(principal.getAuthorities().split(PARAMS_SPLIT));
            for (String per : permission) {
                if (result.contains(per)) {
                    return joinPoint.proceed();
                }
            }
            throw new DeanAuthException("没有权限");

        }

        return joinPoint.proceed();
    }

    @Around("hasUser()")
    public Object isAccessMethodOfHasUser(ProceedingJoinPoint joinPoint) throws Throwable {

        TokenPrincipal principal = principalStorage.getTokenPrincipal();
        if (principal == null) {
            throw new DeanAuthException("没有权限");
        }

        return joinPoint.proceed();
    }

    @Around("hasRole()")
    public Object isAccessMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        TokenPrincipal principal = principalStorage.getTokenPrincipal();
        if (principal == null) {
            throw new DeanAuthException("没有权限");
        }

        //获取访问目标方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();

        // 判断该方法是否加了@HasRole 注解
        if (targetMethod.isAnnotationPresent(HasRole.class)) {

            HasRole hasRole = targetMethod.getAnnotation(HasRole.class);
            String[] permission = hasRole.value();

            List<String> result = Arrays.asList(principal.getRoles().split(PARAMS_SPLIT));
            for (String per : permission) {
                if (result.contains(per)) {
                    return joinPoint.proceed();
                }
            }

            throw new DeanAuthException("没有权限");

        }

        return joinPoint.proceed();
    }

}
