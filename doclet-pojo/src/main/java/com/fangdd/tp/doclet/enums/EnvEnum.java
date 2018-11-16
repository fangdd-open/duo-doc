package com.fangdd.tp.doclet.enums;

/**
 * @auth ycoe
 * @date 18/10/15
 */
public enum EnvEnum {
    DEVELOP("开发环境"),
    TEST("测试环境"),
    BUILD("预发布环境"),
    PRODUCT("正式环境");

    private String name;

    public String getName() {
        return name;
    }

    EnvEnum(String name) {
        this.name = name;
    }
}
