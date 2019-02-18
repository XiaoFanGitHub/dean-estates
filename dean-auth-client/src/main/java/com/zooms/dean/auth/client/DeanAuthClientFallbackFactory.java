package com.zooms.dean.auth.client;

import com.zooms.dean.auth.common.AuthToken;
import com.zooms.dean.auth.common.TokenPrincipal;
import com.zooms.dean.auth.common.bean.*;
import com.zooms.dean.common.web.View;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class DeanAuthClientFallbackFactory implements FallbackFactory<DeanAuthClient> {

    private Logger LOG = LoggerFactory.getLogger(DeanAuthClientFallbackFactory.class);

    @Override
    public DeanAuthClient create(Throwable e) {

        LOG.warn("fallback; reason was: {}", e.getMessage());
        return new DeanAuthClient() {

            @Override
            public View<Map<String, Object>> registerUserEndpoint(Map<String, String> params) {

                return View.ofError("用户注册或修改失败");
            }

            @Override
            public View<TokenPrincipal> requestCheckToken(Map<String, String> parameters) {
                return View.of401Error("认证失败");
            }

            @Override
            public View<AuthToken> requestRefreshToken(Map<String, String> parameters) {
                return View.of401Error("请求刷新token失败");
            }

            @Override
            public View<String> requestAppInit(AppItem appItem) {
                return View.ofError("APP模块初始化失败");
            }

            @Override
            public View<String> requestAuthorityRegister(AuthorityItem authorityItem) {
                return View.ofError("权限注册失败");
            }



            @Override
            public View<String> registerVerificationCodeEndpoint(Map<String, String> params) {
                return View.ofError("验证码发送失败");
            }

            @Override
            public View<String> checkVerificationCodeEndpoint(Map<String, String> params) {
                return View.ofError("验证码校验失败");
            }

            @Override
            public View<UserItem> saveUserEndpoint(UserItem user) {
                return View.ofError("修改用户信息失败");
            }

            @Override
            public View<String> registerUserRoleBindEndpoint(UserRoleItem params) {
                return View.ofError("用户绑定角色失败");
            }

        };
    }
}