package com.zooms.dean.auth.controller.api;

import com.zooms.dean.auth.common.AuthToken;
import com.zooms.dean.auth.common.TokenPrincipal;
import com.zooms.dean.auth.provider.TokenService;
import com.zooms.dean.common.annotation.ApiVersion;
import com.zooms.dean.common.web.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 登录校验对外接口
 *
 * @author linfeng
 */
@RestController
@RequestMapping("/{v}")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    /**
     * 用户登录
     *
     * @param parameters 登录参数
     * @return 返回消息
     */
    @ApiVersion(1)
    @RequestMapping(
            value = {"/token"},
            method = {RequestMethod.POST}
    )
    public View<AuthToken> requestAccessToken(@RequestBody Map<String, String> parameters) {

        return tokenService.requestAccessToken(parameters);
    }

    /**
     * 用户校验
     *
     * @param parameters 校验参数
     * @return 校验消息
     */
    @ApiVersion(1)
    @RequestMapping(
            value = {"/check_token"},
            method = {RequestMethod.POST}
    )
    public View<TokenPrincipal> requestCheckToken(@RequestBody Map<String, String> parameters) {

        return tokenService.requestCheckToken(parameters);
    }

    /**
     * 令牌刷新
     *
     * @param parameters 刷新参数
     * @return 刷新消息
     */
    @ApiVersion(1)
    @RequestMapping(
            value = {"/refresh_token"},
            method = {RequestMethod.POST}
    )
    public View<AuthToken> requestRefreshToken(@RequestBody Map<String, String> parameters) {

        return tokenService.requestRefreshToken(parameters);
    }


    /**
     * 第三方登录
     *
     * @param parameters 登录参数
     * @return 返回消息
     */
    @ApiVersion(1)
    @RequestMapping(
            value = {"/third/login"},
            method = {RequestMethod.POST}
    )
    public View<AuthToken> thirdLogin(@RequestBody Map<String, String> parameters) {

        return tokenService.requestThirdLoginToken(parameters);
    }
    
    /**
     * 绑定第三方账号
     *
     * @param parameters 登录参数
     * @return 返回消息
     */
    @ApiVersion(1)
    @RequestMapping(
            value = {"/bind/login"},
            method = {RequestMethod.POST}
    )
    public View<AuthToken> bindThirdLoginAccount(@RequestBody Map<String, String> parameters) {

        return tokenService.requestBindThirdLoginToken(parameters);
    }
}
