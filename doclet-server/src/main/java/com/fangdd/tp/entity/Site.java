package com.fangdd.tp.entity;

import com.fangdd.tp.doclet.pojo.entity.EnvItem;

import java.util.List;

/**
 * @author xuwenzhen
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

    /**
     * Dubbo ZooKeep配置
     */
    private List<EnvItem> dubbo;

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

    public List<EnvItem> getDubbo() {
        return dubbo;
    }

    public void setDubbo(List<EnvItem> dubbo) {
        this.dubbo = dubbo;
    }
}
