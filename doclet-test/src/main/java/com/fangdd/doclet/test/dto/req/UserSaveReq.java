package com.fangdd.doclet.test.dto.req;

/**
 * Created by xuwenzhen on 2019/3/15.
 */
public class UserSaveReq {
    /**
     * 用户名称
     *
     * @required
     * @demo 张三
     */
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
