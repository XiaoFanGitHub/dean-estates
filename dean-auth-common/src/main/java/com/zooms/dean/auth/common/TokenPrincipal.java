package com.zooms.dean.auth.common;

import com.google.common.base.Joiner;
import com.zooms.dean.common.tool.StringUtils;


import java.util.HashMap;
import java.util.Map;

/**
 * Token
 *
 * @author linfeng
 */
public class TokenPrincipal {

    public static final String PARAMS_SPLIT = ",";
    private Long userId;
    private String username;
    private String mobile;
    private String roles;
    private String authorities;
    private String dealerIds;
    private Map<String, String> tableAuth;
    private Map<String, Object> params = new HashMap<>();

    public TokenPrincipal() {
    }

    public TokenPrincipal(String src) {
    }

    public TokenPrincipal(Long userId, String username, String mobile, String roles, String authorities, String dealerIds,
                          Map<String, String> tableAuth) {
        this.userId = userId;
        this.username = username;
        this.mobile = mobile;
        this.roles = roles;
        this.authorities = authorities;
        this.dealerIds = dealerIds;
        this.tableAuth = tableAuth;
        this.params.put("userId", userId);

        if (StringUtils.isNotBlank(roles)) {
            String[] roleArray = roles.split(PARAMS_SPLIT);
            for (int i = 0; i < roleArray.length; i++) {
                roleArray[i] = "'" + roleArray[i] + "'";
            }
            this.params.put("roles", Joiner.on(PARAMS_SPLIT).join(roleArray));
        }

        if (StringUtils.isNotBlank(authorities)) {
            String[] authoritieArray = authorities.split(PARAMS_SPLIT);
            for (int i = 0; i < authoritieArray.length; i++) {
                authoritieArray[i] = "'" + authoritieArray[i] + "'";
            }
            this.params.put("authorities", Joiner.on(PARAMS_SPLIT).join(authoritieArray));
        }

        if (StringUtils.isNotBlank(dealerIds)) {
            String[] dealerIdsArray = dealerIds.split(PARAMS_SPLIT);
            for (int i = 0; i < dealerIdsArray.length; i++) {
                dealerIdsArray[i] = "'" + dealerIdsArray[i] + "'";
            }
            this.params.put("dealerIds", Joiner.on(PARAMS_SPLIT).join(dealerIdsArray));
        }

    }

    public String getUsername() {
        return username;
    }

    public String getRoles() {
        return roles;
    }

    public String getAuthorities() {
        return authorities;
    }

    public Long getUserId() {
        return userId;
    }

    public Map<String, String> getTableAuth() {
        return tableAuth;
    }

    public String getDealerIds() {
        return dealerIds;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public String getMobile() {
        return mobile;
    }
}
