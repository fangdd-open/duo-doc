package com.fangdd.tp.dto.response;

import com.fangdd.traffic.common.mongo.annotation.AutoIncrement;

/**
 *
 * @author xuwenzhen
 * @date 2019/3/8
 */
public class SimpleUserDto {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * Email
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
