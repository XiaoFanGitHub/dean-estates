package com.zooms.dean.gateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import com.zooms.dean.common.tool.JsonUtils;
import com.zooms.dean.common.web.View;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ERROR_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_ERROR_FILTER_ORDER;

/**
 * project：dean-cloud
 *
 * @author linfeng @ nondo
 * @date 2018/6/19
 */
@Component
public class GatewayErrorFilter extends ZuulFilter {

    private static final Log log = LogFactory.getLog(GatewayErrorFilter.class);


    @Override
    public String filterType() {
        return ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_ERROR_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        // only forward to errorPath if it hasn't been forwarded to already
        return ctx.getThrowable() != null;
    }

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            ZuulException exception = findZuulException(ctx.getThrowable());
            log.error(exception.getLocalizedMessage(), exception);

            View<Void> stringView = View.ofError("路由异常");
            if (!ctx.getResponse().isCommitted()) {
                responseOutJson(ctx.getResponse(), JsonUtils.nonDefaultMapper().toJson(stringView));
            }

        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage(), ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }

    private void responseOutJson(HttpServletResponse response, String json) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            response.getWriter().append(json);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    private ZuulException findZuulException(Throwable throwable) {
        if (throwable.getCause() instanceof ZuulRuntimeException) {
            // this was a failure initiated by one of the local filters
            return (ZuulException) throwable.getCause().getCause();
        }

        if (throwable.getCause() instanceof ZuulException) {
            // wrapped zuul exception
            return (ZuulException) throwable.getCause();
        }

        if (throwable instanceof ZuulException) {
            // exception thrown by zuul lifecycle
            return (ZuulException) throwable;
        }

        // fallback, should never get here
        return new ZuulException(throwable, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
    }

}
