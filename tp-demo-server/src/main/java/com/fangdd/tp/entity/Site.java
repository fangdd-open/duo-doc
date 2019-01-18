package com.fangdd.tp.entity;

import java.util.List;

/**
 * @auth ycoe
 * @date 18/11/28
 */
public class Site {
    /**
     * 团队代码
     */
    private String id;

    /**
     * 团队名称
     */
    private String name;

    /**
     * Logo
     */
    private String logo;

    /**
     * 域名
     */
    private List<String> domains;

    /**
     * 可用的登录授权服务代码s
     */
    private List<String> auths;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<String> getDomains() {
        return domains;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    public List<String> getAuths() {
        return auths;
    }

    public void setAuths(List<String> auths) {
        this.auths = auths;
    }
}
