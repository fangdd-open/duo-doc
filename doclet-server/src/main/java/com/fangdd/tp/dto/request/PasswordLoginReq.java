package com.fangdd.tp.dto.request;

/**
 *
 * @author xuwenzhen
 * @date 2019/12/20
 */
public class PasswordLoginReq {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
