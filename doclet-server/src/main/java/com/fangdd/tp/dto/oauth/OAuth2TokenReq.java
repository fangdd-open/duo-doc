package com.fangdd.tp.dto.oauth;

/**
 * @auth ycoe
 * @date 18/11/28
 */
public class OAuth2TokenReq {
    /**
     * 团队代码
     */
    private String site;

    /**
     * 三方平台回调返回的token
     */
    private String token;

    /**
     * 状态值，是一个安全参数，如何提供则原样返回，你可以在请求前随机生成并缓存起来，当用户授权后与缓存中的值对比，可防止重放攻击
     */
    private String state;

    /**
     * 返回的URL
     */
    private String returnUrl;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
