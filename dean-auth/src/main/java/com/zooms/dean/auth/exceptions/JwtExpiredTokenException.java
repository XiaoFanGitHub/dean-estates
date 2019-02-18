package com.zooms.dean.auth.exceptions;


import com.zooms.dean.auth.provider.token.JwtToken;
import io.jsonwebtoken.JwtException;

/**
 * JwtExpiredTokenException
 *
 * @author linfeng
 * @since 2017/7/30
 */
public class JwtExpiredTokenException extends JwtException {
    private static final long serialVersionUID = -5959543783324224864L;

    private JwtToken token;

    public JwtExpiredTokenException(String msg) {
        super(msg);
    }

    public JwtExpiredTokenException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}
