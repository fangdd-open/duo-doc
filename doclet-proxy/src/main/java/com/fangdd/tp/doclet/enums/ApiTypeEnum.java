package com.fangdd.tp.doclet.enums;

/**
 * @auth ycoe
 * @date 18/1/12
 */
public enum ApiTypeEnum {
    RESTFUL(0),

    DUBBO(1);

    private Integer type;

    ApiTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
