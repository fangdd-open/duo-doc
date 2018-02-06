package com.fangdd.tp.dto;

/**
 * @auth ycoe
 * @date 18/1/26
 */
public class Person {
    /**
     * 全名
     * @demo 人类
     */
    private String fullName;

    /**
     * 基类名称
     * @demo 基类
     */
    private String name;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
