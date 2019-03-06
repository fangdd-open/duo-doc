package com.fangdd.tp.dto.oauth;

/**
 * @auth ycoe
 * @date 18/11/29
 */
public class OAuth2UserInfo {
    private String id;

    private String name;
    private String email;
    private String mobile;

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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }
}
