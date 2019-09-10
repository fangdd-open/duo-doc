package com.fangdd.tp.doclet.enums;

/**
 * @author xuwenzhen
 * @date 18/1/15
 */
public enum ApiPositionEnum {
    RESPONSE("响应体"),

    PARAMETER("参数");

    private String title;

    ApiPositionEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
