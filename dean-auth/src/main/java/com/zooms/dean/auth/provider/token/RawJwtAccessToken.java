package com.zooms.dean.auth.provider.token;

import com.zooms.dean.auth.exceptions.JwtExpiredTokenException;
import com.zooms.dean.auth.exceptions.JwtInvalidTokenException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RawJwtAccessToken implements JwtToken {

    private static Logger LOG = LoggerFactory.getLogger(RawJwtAccessToken.class);
    private final String token;

    RawJwtAccessToken(final String token) {
        this.token = token;
    }

    public Claims parseClaims(String signingKey) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token).getBody();
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            LOG.error("Invalid JWT Token", ex);
            throw new JwtInvalidTokenException(this, "Invalid JWT token: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            LOG.info("JWT Token is expired", expiredEx);
            throw new JwtExpiredTokenException(this, "JWT Token expired", expiredEx);
        }
    }

    @Override
    public String getToken() {
        return token;
    }
}
