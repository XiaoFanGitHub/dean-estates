package com.zooms.dean.auth.client;

import com.zooms.dean.auth.common.AuthToken;
import com.zooms.dean.auth.common.TokenPrincipal;
import com.zooms.dean.auth.common.bean.*;
import com.zooms.dean.common.web.View;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * 权限客户端
 *
 * @author linfeng
 */
@FeignClient(value = "${dean.auth.security.server:dean-auth}", fallbackFactory = DeanAuthClientFallbackFactory.class)
public interface DeanAuthClient {


    /**
     * 修改注册用户
     *
     * @param params {
     *               "username":"",
     *               "password":""
     *               }
     * @return
     */
    @RequestMapping(
            value = "/auth/v1/user/register",
            method = {RequestMethod.POST}
    )
    View<Map<String, Object>> registerUserEndpoint(@RequestBody Map<String, String> params);

    /**
     * token验证
     *
     * @param parameters 请求参数
     * @return 返回消息
     */
    @RequestMapping(
            value = {"/auth/v1/check_token"},
            method = {RequestMethod.POST}
    )
    View<TokenPrincipal> requestCheckToken(@RequestBody Map<String, String> parameters);


    /**
     * 请求token刷新
     *
     * @param parameters 请求参数
     * @return 返回消息
     */
    @RequestMapping(
            value = {"/auth/v1/refresh_token"},
            method = {RequestMethod.POST}
    )
    View<AuthToken> requestRefreshToken(@RequestBody Map<String, String> parameters);

    /**
     * 应用初始化
     *
     * @param appItem 应用对象参数
     * @return 返回消息
     */
    @RequestMapping(
            value = "/auth/v1/app/init",
            method = RequestMethod.POST
    )
    View<String> requestAppInit(@RequestBody AppItem appItem);

    /**
     * 权限注册
     *
     * @param authorityItem 权限请求对象
     * @return 返回消息
     */
    @RequestMapping(
            value = "/auth/v1/authority/register",
            method = RequestMethod.POST
    )
    View<String> requestAuthorityRegister(@RequestBody AuthorityItem authorityItem);


    /**
     * 手机邮箱发送验证码
     *
     * @param params {
     *               username:"手机号",
     *               send_type:"login/register/modify/common"
     *               }
     * @return
     */
    @RequestMapping(
            value = "/auth/v1/verification_code/send",
            method = {RequestMethod.POST}
    )
    View<String> registerVerificationCodeEndpoint(@RequestBody Map<String, String> params);

    /**
     * 验证码有效性校验
     *
     * @param params {
     *               username:"手机号",
     *               code:"验证码"
     *               }
     * @return
     */
    @RequestMapping(
            value = "/auth/v1/verification_code/check",
            method = {RequestMethod.POST}
    )
    View<String> checkVerificationCodeEndpoint(@RequestBody Map<String, String> params);


    @RequestMapping(
            value = "/auth/v1/user/save",
            method = {RequestMethod.POST}
    )
    View<UserItem> saveUserEndpoint(@RequestBody UserItem user);


    @RequestMapping(
            value = "/auth/v1/user/role/bind",
            method = {RequestMethod.POST}
    )
    View<String> registerUserRoleBindEndpoint(@Validated @RequestBody UserRoleItem params);

}
