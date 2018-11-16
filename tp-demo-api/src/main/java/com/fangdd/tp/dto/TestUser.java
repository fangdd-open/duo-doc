package com.fangdd.tp.dto;

import com.fangdd.tp.enums.BillStatus;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户基本信息
 *
 * @auth ycoe
 * @date 18/1/5
 */
public class TestUser<T> {
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
     * 业务状态
     */
    private BillStatus status;

    /**
     * 地址
     *
     * @demo 科兴科学园B2-18楼
     */
    private String address;

    /**
     * 上级用户
     */
    private T parent;

    /**
     * 更新时间
     */
    @NotNull
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

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public T getParent() {
        return parent;
    }

    public void setParent(T parent) {
        this.parent = parent;
    }
}
