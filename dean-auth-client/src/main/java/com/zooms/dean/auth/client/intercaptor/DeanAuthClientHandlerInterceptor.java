package com.zooms.dean.auth.client.intercaptor;


import com.zooms.dean.auth.common.AbstractDeanAuthHandlerInterceptor;
import com.zooms.dean.auth.common.TokenPrincipal;
import com.zooms.dean.auth.common.TokenPrincipalProvider;
import com.zooms.dean.common.exceptions.DeanAuthException;
import com.zooms.dean.common.web.View;
import com.zooms.dean.auth.client.DeanAuthClient;
import com.zooms.dean.auth.client.DeanAuthClientProperties;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.zooms.dean.auth.common.WebAuthUtils.DEAN_AUTH_AUTHORIZATION;
import static com.zooms.dean.auth.common.WebAuthUtils.DEAN_AUTH_TOKEN;

/**
 * @author slacrey
 * @since 2018/1/20
 */
public class DeanAuthClientHandlerInterceptor extends AbstractDeanAuthHandlerInterceptor {

    private final DeanAuthClient deanAuthClient;
    private final TokenPrincipalProvider tokenPrincipalProvider;

    public DeanAuthClientHandlerInterceptor(DeanAuthClientProperties deanAuthClientProperties,
                                            DeanAuthClient deanAuthClient, TokenPrincipalProvider tokenPrincipalProvider) {
        super(deanAuthClientProperties);
        this.deanAuthClient = deanAuthClient;
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
        View<TokenPrincipal> tokenPrincipalView = deanAuthClient.requestCheckToken(params);
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
