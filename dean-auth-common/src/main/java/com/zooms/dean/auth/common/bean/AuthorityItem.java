package com.zooms.dean.auth.common.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linfeng
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorityItem implements Serializable {
    private static final long serialVersionUID = -8151710193295779832L;

    private Long id;
    private String code;
    private String name;
    private String tableName;
    private String domainForce;

    private Map<String, List<Long>> roles = new HashMap<>();
    private Boolean global;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomainForce() {
        return domainForce;
    }

    public void setDomainForce(String domainForce) {
        this.domainForce = domainForce;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public Map<String, List<Long>> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, List<Long>> roles) {
        this.roles = roles;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
