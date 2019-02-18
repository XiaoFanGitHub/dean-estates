package com.zooms.dean.auth.provider.token;

import java.io.Serializable;
import java.util.Collection;

/**
 * 用户凭证
 *
 * @author linfeng
 */
public class UserPrincipal implements Serializable {

    private static final long serialVersionUID = -675376249146040814L;
    private final Long userId;
    private final String username;
    private final String mobile;
    private final Collection<String> roles;
    private final Collection<String> authorities;


    public UserPrincipal(Long userId, String username, String mobile, Collection<String> roles, Collection<String> authorities) {
        this.userId = userId;
        this.username = username;
        this.mobile = mobile;
        this.roles = roles;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public Collection<String> getAuthorities() {
        return authorities;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public Long getUserId() {
        return userId;
    }

    public String getMobile() {
        return mobile;
    }
}
