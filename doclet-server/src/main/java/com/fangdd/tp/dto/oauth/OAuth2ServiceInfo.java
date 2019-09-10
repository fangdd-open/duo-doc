package com.fangdd.tp.dto.oauth;

/**
 * @author xuwenzhen
 * @date 18/11/29
 */
public class OAuth2ServiceInfo {
    /**
     * OAuth2服务名称，比如: fdd / wx
     */
    private String serverName;

    /**
     * 获取登录链接URL（不带参数部分）
     */
    private String loginUrlApi;

    /**
     * 获取用户accessToken URL（不带参数部分）
     */
    private String accessTokenApi;

    /**
     * 通过accessToken获取用户信息URL（不带参数部分）
     */
    private String userInfoApi;

    private String clientId;

    private String clientSecret;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getLoginUrlApi() {
        return loginUrlApi;
    }

    public void setLoginUrlApi(String loginUrlApi) {
        this.loginUrlApi = loginUrlApi;
    }

    public String getAccessTokenApi() {
        return accessTokenApi;
    }

    public void setAccessTokenApi(String accessTokenApi) {
        this.accessTokenApi = accessTokenApi;
    }

    public String getUserInfoApi() {
        return userInfoApi;
    }

    public void setUserInfoApi(String userInfoApi) {
        this.userInfoApi = userInfoApi;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
