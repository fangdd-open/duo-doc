package com.fangdd.tp.entity;

/**
 * @author xuwenzhen
 * @date 18/11/28
 */
public class AuthInfo {
    /**
     * 三方授权平台代码，比如: fdd
     */
    private String code;

    /**
     * 三方授权系统唯一ID
     */
    private String gid;

    /**
     *
     */
    private String accessToken;

    /**
     * 用于刷新的token
     */
    private String refreshToken;

    /**
     * 密码
     */
    private String password;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
