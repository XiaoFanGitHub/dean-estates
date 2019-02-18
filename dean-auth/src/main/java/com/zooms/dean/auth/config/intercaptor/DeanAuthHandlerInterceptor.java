package com.zooms.dean.auth.config.intercaptor;

import com.zooms.dean.auth.common.AbstractDeanAuthHandlerInterceptor;
import com.zooms.dean.auth.common.TokenPrincipal;
import com.zooms.dean.auth.common.TokenPrincipalProvider;
import com.zooms.dean.auth.config.DeanAuthServerSettings;
import com.zooms.dean.auth.provider.TokenService;
import com.zooms.dean.common.exceptions.DeanAuthException;
import com.zooms.dean.common.web.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.zooms.dean.auth.common.WebAuthUtils.DEAN_AUTH_AUTHORIZATION;
import static com.zooms.dean.auth.common.WebAuthUtils.DEAN_AUTH_TOKEN;

/**
 * MVC拦截器配置
 *
 * @author slacrey
 * @since 2018/1/20
 */
@Component
public class DeanAuthHandlerInterceptor extends AbstractDeanAuthHandlerInterceptor {

    private final TokenService tokenService;
    private final TokenPrincipalProvider tokenPrincipalProvider;

    @Autowired
    public DeanAuthHandlerInterceptor(DeanAuthServerSettings deanAuthServerSettings,
                                      TokenService tokenService, TokenPrincipalProvider tokenPrincipalProvider) {
        super(deanAuthServerSettings);
        this.tokenService = tokenService;
        this.tokenPrincipalProvider = tokenPrincipalProvider;
    }


    @Override
    protected TokenPrincipal getTokenPrincipal() {
        return tokenPrincipalProvider.getTokenPrincipal();
    }

    @Override
    protected boolean doHandleAuthentication(HttpServletRequest request) {
        String token = request.getHeader(DEAN_AUTH_AUTHORIZATION);
        if (StringUtils.isEmpty(token)) {
            throw new DeanAuthException("权限错误");
        }
        Map<String, String> params = new HashMap<>();
        params.put(DEAN_AUTH_TOKEN, token);
        View<TokenPrincipal> tokenPrincipalView = tokenService.requestCheckToken(params);
        if (tokenPrincipalView != null) {
            if (tokenPrincipalView.getCode() == View.STATE_OK) {
                tokenPrincipalProvider.putTokenPrincipal(tokenPrincipalView.getData());
                return true;
            } else {
                throw new DeanAuthException(tokenPrincipalView.getMessage());
            }
        }
        throw new DeanAuthException("权限错误");
    }

}
