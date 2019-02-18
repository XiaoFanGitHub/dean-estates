package com.zooms.dean.auth.common;

/**
 * project：dean-cloud
 *
 * @author linfeng @ nondo
 * @date 2018/4/18
 */
public class DeanAuthSecurityProperties {

    public Boolean global = Boolean.TRUE;
    public String[] authPath;
    public String[] skipPath;

    public Boolean getGlobal() {
        if (authPath != null && authPath.length > 0) {
            return Boolean.FALSE;
        }
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public String[] getAuthPath() {
        return authPath;
    }

    public void setAuthPath(String[] authPath) {
        this.authPath = authPath;
    }

    public String[] getSkipPath() {
        return skipPath;
    }

    public void setSkipPath(String[] skipPath) {
        this.skipPath = skipPath;
    }


}
