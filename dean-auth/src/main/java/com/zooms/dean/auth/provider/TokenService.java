package com.zooms.dean.auth.provider;


import com.zooms.dean.auth.common.AuthToken;
import com.zooms.dean.auth.common.TokenPrincipal;
import com.zooms.dean.common.web.View;

import java.util.Map;

public interface TokenService {

    /**
     * 请求访问令牌
     *
     * @param params
     * @return
     */
    View<AuthToken> requestAccessToken(Map<String, String> params);

    /**
     * 检查令牌
     * @param params
     * @return
     */
    View<TokenPrincipal> requestCheckToken(Map<String, String> params);


    /**
     * 请求刷新令牌
     * @param params
     * @return
     */
    View<AuthToken> requestRefreshToken(Map<String, String> params);
    
    /**
     * 第三方登录
     *
     * @param params
     * @return
     */
    View<AuthToken> requestThirdLoginToken(Map<String, String> params);

    /**
     * 绑定第三方账号
     *
     * @param parameters
     * @return
     */
    View<AuthToken> requestBindThirdLoginToken(Map<String, String> parameters);



}
