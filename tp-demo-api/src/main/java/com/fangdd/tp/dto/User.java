package com.fangdd.tp.dto;

import java.util.List;

/**
 * 用户基本信息
 *
 * @auth ycoe
 * @date 18/1/5
 */
public class User<T> extends Person {
    /**
     * 用户ID
     *
     * @demo 123456
     */
    private Long id;

    /**
     * 用户名称
     *
     * @demo 张三
     */
    private String name;

    /**
     * 地址
     *
     * @demo 科兴科学园B2-18楼
     */
    private String address;

    /**
     * 角色
     */
    private List<T> roles;

    /**
     * 上级用户
     */
    private User<T> parent;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<T> getRoles() {
        return roles;
    }

    public void setRoles(List<T> roles) {
        this.roles = roles;
    }

    public User<T> getParent() {
        return parent;
    }

    public void setParent(User<T> parent) {
        this.parent = parent;
    }
}
