package com.fangdd.tp.entity;

import java.util.Map;

/**
 * @auth ycoe
 * @date 18/11/28
 */
public class OAuth2Conf {
    /**
     * ID： {site}-{oauth}
     */
    private String id;

    /**
     * 网站名称
     */
    private String site;

    /**
     * OAuth2服务名称
     */
    private String oauth;

    /**
     * OAuth2服务配置
     */
    private Map<String, String> conf;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getOauth() {
        return oauth;
    }

    public void setOauth(String oauth) {
        this.oauth = oauth;
    }

    public Map<String, String> getConf() {
        return conf;
    }

    public void setConf(Map<String, String> conf) {
        this.conf = conf;
    }
}
