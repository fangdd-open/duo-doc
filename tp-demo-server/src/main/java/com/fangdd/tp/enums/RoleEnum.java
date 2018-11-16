package com.fangdd.tp.enums;

/**
 * @auth ycoe
 * @date 18/11/28
 */
public enum RoleEnum {
    /**
     * 普通用户
     */
    USER(1),

    /**
     * 超级管理员
     */
    ADMIN(100);

    private int role;

    RoleEnum(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }
}
