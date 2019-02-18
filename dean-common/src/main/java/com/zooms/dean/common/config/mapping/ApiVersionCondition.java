package com.zooms.dean.common.config.mapping;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 首先，匹配过滤出当前访问接口中是否存在v(1-9)，
 * 如果存在，继续判断格式是否对，否则报错返回。
 * 其次，比较请求地址中版本与控制器中版本，如果
 * 请求的版本值大于控制器中版本值，则取控制器版本
 * 值继续接口后续访问操作。
 * @author linfeng
 */
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("v(\\d+)/");
    private final int apiVersion;

    public ApiVersionCondition(int apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        return new ApiVersionCondition(other.getApiVersion());
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {

        Matcher matcher = VERSION_PREFIX_PATTERN.matcher(request.getRequestURI());
        if (matcher.find()) {
            Integer version = Integer.valueOf(matcher.group(1));
            if (version >= this.apiVersion) {
                return this;
            }
        }
        return null;
    }

    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {

        return other.getApiVersion() - this.apiVersion;
    }

    public int getApiVersion() {
        return apiVersion;
    }
}
