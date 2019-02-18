package com.zooms.dean.auth.provider.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;

public final class JwtAccessToken implements JwtToken {

    private final String token;
    @JsonIgnore
    private Claims claims;

    JwtAccessToken(final String accessToken, final Claims claims) {
        this.token = accessToken;
        this.claims = claims;
    }

    @Override
    public String getToken() {
        return token;
    }

    public Claims getClaims() {
        return claims;
    }
}
