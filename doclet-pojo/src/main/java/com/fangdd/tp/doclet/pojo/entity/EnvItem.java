package com.fangdd.tp.doclet.pojo.entity;

import com.fangdd.tp.doclet.enums.EnvEnum;

/**
 * @auth ycoe
 * @date 18/10/26
 */
public class EnvItem {
    /**
     * 环境代码
     *
     * @demo TEST
     * @see EnvEnum
     */
    private String code;

    /**
     * 环境名称
     */
    private String name;

    /**
     * 接口地址
     */
    private String url;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
