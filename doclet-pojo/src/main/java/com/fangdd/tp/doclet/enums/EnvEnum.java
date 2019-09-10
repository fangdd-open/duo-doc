package com.fangdd.tp.doclet.enums;

/**
 * @author xuwenzhen
 * @date 18/10/15
 */
public enum EnvEnum {
    /**
     * 开发环境
     */
    DEVELOP("开发环境"),

    /**
     * 测试环境
     */
    TEST("测试环境"),

    /**
     * 预发布环境
     */
    BUILD("预发布环境"),

    /**
     * 正式环境
     */
    PRODUCT("正式环境");

    private String name;

    EnvEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
