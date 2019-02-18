package com.zooms.dean.auth.common;


import com.zooms.dean.auth.common.annotation.HasPermission;
import com.zooms.dean.auth.common.annotation.HasRole;
import com.zooms.dean.auth.common.annotation.HasUser;
import com.zooms.dean.common.exceptions.DeanAuthException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static com.zooms.dean.auth.common.TokenPrincipal.PARAMS_SPLIT;


/**
 * project：dean-cloud
 *
 * @author linfeng @ nondo
 * @date 2018/4/18
 */
public abstract class AbstractDeanAuthHandlerInterceptor implements HandlerInterceptor {

    private final Boolean globalAuthentication;

    public AbstractDeanAuthHandlerInterceptor(DeanAuthSecurityProperties deanAuthSecurityProperties) {
        this.globalAuthentication = deanAuthSecurityProperties.getGlobal();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (requireAuthentication(handlerMethod)) {
            return doHandleAuthentication(request) && detailAuthentication(handlerMethod);
        } else {
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

    /**
     * 获得TokenPrincipal对象 {@link TokenPrincipal}
     *
     * @return TokenPrincipal 对象
     */
    protected abstract TokenPrincipal getTokenPrincipal();

    /**
     * 真正的拦截处理
     *
     * @param request request对象 {@link HttpServletRequest }
     * @return 是否需要被拦截
     */
    protected abstract boolean doHandleAuthentication(HttpServletRequest request);

    /**
     * 需要认证
     *
     * @param handler 需要认证的对象
     * @return 是否需要认证
     */
    private boolean requireAuthentication(HandlerMethod handler) {
        return this.globalAuthentication || handler.hasMethodAnnotation(HasUser.class) || handler.hasMethodAnnotation(HasRole.class)
                || handler.hasMethodAnnotation(HasPermission.class);
    }

    /**
     * 处理认证
     *
     * @param handler 需要认证的对象
     * @return 是否需要认证
     */
    private boolean detailAuthentication(HandlerMethod handler) {

        if (handler.hasMethodAnnotation(HasUser.class)) {

            return isAccessMethodOfHasUser();
        } else if (handler.hasMethodAnnotation(HasRole.class)) {

            return isAccessMethodOfHasRole(handler);
        } else if (handler.hasMethodAnnotation(HasPermission.class)) {

            return isAccessMethodOfHasPermission(handler);
        } else {
            return true;
        }
    }


    /**
     * 是否有权限访问@HasUser注解的方法
     *
     * @return 是否有权限
     * @throws DeanAuthException 权限异常
     */
    private boolean isAccessMethodOfHasUser() throws DeanAuthException {

        TokenPrincipal principal = getTokenPrincipal();
        if (principal == null) {
            throw new DeanAuthException("用户没有登录或者没有配置URL地址需要权限");
        }
        return true;

    }

    /**
     * 是否有权限访问@HasRole注解的方法
     *
     * @return 是否有权限
     * @throws DeanAuthException 权限异常
     */
    private boolean isAccessMethodOfHasRole(HandlerMethod handler) throws DeanAuthException {

        TokenPrincipal principal = getTokenPrincipal();
        if (principal == null) {
            throw new DeanAuthException("用户没有登录或者没有配置URL地址需要权限");
        }

        HasRole hasRole = handler.getMethodAnnotation(HasRole.class);
        String[] permission = hasRole.value();

        List<String> result = Arrays.asList(principal.getRoles().split(PARAMS_SPLIT));
        for (String per : permission) {
            if (result.contains(per)) {
                return true;
            }
        }
        throw new DeanAuthException("没有权限");

    }

    /**
     * 是否有权限访问@HasPermission注解的方法
     *
     * @return 是否有权限
     * @throws DeanAuthException 权限异常
     */
    private boolean isAccessMethodOfHasPermission(HandlerMethod handler) throws DeanAuthException {

        TokenPrincipal principal = getTokenPrincipal();
        if (principal == null) {
            throw new DeanAuthException("用户没有登录或者没有配置URL地址需要权限");
        }

        HasPermission hasPermission = handler.getMethodAnnotation(HasPermission.class);
        String[] permission = hasPermission.value();
        List<String> result = Arrays.asList(principal.getAuthorities().split(PARAMS_SPLIT));
        for (String per : permission) {
            if (result.contains(per)) {
                return true;
            }
        }
        throw new DeanAuthException("没有权限");

    }
}