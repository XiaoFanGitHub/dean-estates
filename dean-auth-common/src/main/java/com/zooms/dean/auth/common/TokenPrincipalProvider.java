package com.zooms.dean.auth.common;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * token 凭证Provider
 *
 * @author linfeng
 */
public class TokenPrincipalProvider implements Serializable {

    private static final long serialVersionUID = 4267364993213057785L;
    private final Map<String, Object> storeMap;

    public TokenPrincipalProvider() {
        this.storeMap = new ConcurrentHashMap<>();
    }

    public TokenPrincipal getTokenPrincipal() {

        return (TokenPrincipal) storeMap.get(WebAuthUtils.DEAN_AUTH_PRINCIPAL);
    }

    public void putTokenPrincipal(TokenPrincipal principal) {

        storeMap.put(WebAuthUtils.DEAN_AUTH_PRINCIPAL, principal);
    }

}
