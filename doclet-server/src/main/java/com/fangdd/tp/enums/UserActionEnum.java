package com.fangdd.tp.enums;

/**
 * @auth ycoe
 * @date 18/12/4
 */
public enum UserActionEnum {
    LOGIN(1, "用户登录"),
    LOGOUT(2, "用户注销"),
    DOC_CREATE(3, "文档创建"),

    REST_FUL_INVOKE(10, "RestFul接口调用"),
    DUBBO_INVOKE(11, "Dubbo接口调用"),
    INVOKE_REQUEST_SAVE(12, "接口调用参数保存"),
    INVOKE_REQUEST_DELETE(13, "接口调用参数删除")

    ;
    private Integer action;

    private String actionName;

    UserActionEnum(Integer action, String actionName) {
        this.action = action;
        this.actionName = actionName;
    }

    public Integer getAction() {
        return action;
    }

    public String getActionName() {
        return actionName;
    }
}
