package com.zooms.dean.common.tool;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 获取客户端信息工具类
 * @author zhaolijin
 * @date 2018-06-21
 */
public class RequestUtils {

    /**
     * 获取请求URL
     * @param request
     * @return
     */
    public static String getRequestUrl(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    /**
     * 获取客户端操作系统信息
     * 
     * @param request
     * @return
     */
    public static String getClientInfo(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String os = "";
        // =================OS Info=======================
        if (userAgent.toLowerCase().indexOf("windows") >= 0) {
            os = "Windows";
        } else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
            os = "Mac";
        } else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
            os = "Unix";
        } else if (userAgent.toLowerCase().indexOf("android") >= 0) {
            os = "Android";
        } else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
            os = "IPhone";
        } else {
            os = "UnKnown, More-Info: " + userAgent;
        }

        return os;
    }

    public static void main(String[] args) {}
}
