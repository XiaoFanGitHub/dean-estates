package com.zooms.dean.auth.provider.token;

import com.google.common.base.Joiner;
import com.zooms.dean.auth.config.DeanAuthJwtSettings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * JwtToken
 *
 * @author linfeng
 * @since 2017/7/30
 */
@Component
public class JwtTokenFactory {

    public static final String TOKEN_PARAMS_MOBILE = "mobile";
    public static final String TOKEN_PARAMS_ROLES = "roles";
    public static final String TOKEN_PARAMS_AUTHORITIES = "authorities";
    public static final String TOKEN_PARAMS_USER_ID = "user_id";
    public static final String TOKEN_PARAMS_SPLIT = ",";
    private final DeanAuthJwtSettings settings;

    @Autowired
    public JwtTokenFactory(DeanAuthJwtSettings deanAuthJwtSettings) {
        this.settings = deanAuthJwtSettings;
    }

    /**
     * 创建access token
     *
     * @param principal   用户信息
     * @param otherClaims 额外参数
     * @return JwtToken
     */
    public JwtToken createAccessJwtToken(UserPrincipal principal, Map<String, Object> otherClaims) {
        if (StringUtils.isEmpty(principal.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        Claims claims = Jwts.claims().setSubject(principal.getUsername());
        claims.put(TOKEN_PARAMS_MOBILE, principal.getMobile());
        claims.put(TOKEN_PARAMS_USER_ID, principal.getUserId());
        claims.put(TOKEN_PARAMS_ROLES, Joiner.on(TOKEN_PARAMS_SPLIT).join(principal.getRoles()));
        claims.put(TOKEN_PARAMS_AUTHORITIES, Joiner.on(TOKEN_PARAMS_SPLIT).join(principal.getAuthorities()));

        for (Map.Entry<String, Object> entry : otherClaims.entrySet()) {
            claims.put(entry.getKey(), entry.getValue());
        }

        LocalDateTime currentTime = LocalDateTime.now();

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuer(settings.getTokenIssuer())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(settings.getAccessTokenExpTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();

        return new JwtAccessToken(accessToken, claims);
    }

    /**
     * 创建access token
     *
     * @param rawToken 原始token
     * @return JwtToken
     */
    public JwtToken createAccessJwtToken(String rawToken) {

        Claims rawClaims = new RawJwtAccessToken(rawToken).parseClaims(settings.getTokenSigningKey());
        Claims claims = Jwts.claims().setSubject(rawClaims.getSubject());
        for (Map.Entry<String, Object> entry : rawClaims.entrySet()) {
            claims.put(entry.getKey(), entry.getValue());
        }

        LocalDateTime currentTime = LocalDateTime.now();

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuer(settings.getTokenIssuer())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(settings.getAccessTokenExpTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();

        return new JwtAccessToken(accessToken, claims);
    }

    /**
     * 创建 refresh token
     *
     * @param principal   用户信息
     * @param otherClaims 额外信息
     * @return JwtToken
     */
    public JwtToken createRefreshToken(UserPrincipal principal, Map<String, Object> otherClaims) {

        if (StringUtils.isEmpty(principal.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims().setSubject(principal.getUsername());
        claims.put(TOKEN_PARAMS_MOBILE, principal.getMobile());
        claims.put(TOKEN_PARAMS_USER_ID, principal.getUserId());
        claims.put(TOKEN_PARAMS_ROLES, Joiner.on(TOKEN_PARAMS_SPLIT).join(principal.getRoles()));
        claims.put(TOKEN_PARAMS_AUTHORITIES, Joiner.on(TOKEN_PARAMS_SPLIT).join(principal.getAuthorities()));

        for (Map.Entry<String, Object> entry : otherClaims.entrySet()) {
            claims.put(entry.getKey(), entry.getValue());
        }

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuer(settings.getTokenIssuer())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(settings.getRefreshTokenExpTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();

        return new JwtAccessToken(refreshToken, claims);
    }

    /**
     * 创建 refresh token
     *
     * @param rawToken 原始token
     * @return JwtToken
     */
    public JwtToken createRefreshToken(String rawToken) {

        Claims rawClaims = new RawJwtAccessToken(rawToken).parseClaims(settings.getTokenSigningKey());
        Claims claims = Jwts.claims().setSubject(rawClaims.getSubject());
        for (Map.Entry<String, Object> entry : rawClaims.entrySet()) {
            claims.put(entry.getKey(), entry.getValue());
        }

        LocalDateTime currentTime = LocalDateTime.now();

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuer(settings.getTokenIssuer())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(settings.getRefreshTokenExpTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();

        return new JwtAccessToken(accessToken, claims);
    }

    public Claims parseToken(String rawToken) {
        return new RawJwtAccessToken(rawToken).parseClaims(settings.getTokenSigningKey());
    }

}
