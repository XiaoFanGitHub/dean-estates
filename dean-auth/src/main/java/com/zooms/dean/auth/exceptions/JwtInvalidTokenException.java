package com.zooms.dean.auth.exceptions;

import com.zooms.dean.auth.provider.token.JwtToken;
import io.jsonwebtoken.JwtException;

/**
 * JwtInvalidTokenException
 *
 * @author linfeng
 * @since 2017/7/30
 */
public class JwtInvalidTokenException extends JwtException {
    private static final long serialVersionUID = -294671188037098603L;

    private JwtToken token;

    public JwtInvalidTokenException(String msg) {
        super(msg);
    }

    public JwtInvalidTokenException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }

}
