package com.zooms.dean.auth.common.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppItem implements Serializable {
    private static final long serialVersionUID = -7991573575528455465L;

    @NotBlank(message = "应用模块的appKey不能为空")
    private String appKey;
    private String appName;
    private Integer notUpdate = 1;


    private Map<String, List<AuthorityItem>> authorities = new HashMap<>();

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getNotUpdate() {
        return notUpdate;
    }

    public void setNotUpdate(Integer notUpdate) {
        this.notUpdate = notUpdate;
    }


    public Map<String, List<AuthorityItem>> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Map<String, List<AuthorityItem>> authorities) {
        this.authorities = authorities;
    }
}
