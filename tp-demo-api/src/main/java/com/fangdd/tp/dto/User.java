package com.fangdd.tp.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
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

    /**
     * 更新时间
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy+MM+dd")
    private Date updateTime;

    /**
     * 财产
     */
    private BigDecimal money;

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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
