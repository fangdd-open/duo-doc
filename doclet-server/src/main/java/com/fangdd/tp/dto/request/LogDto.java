package com.fangdd.tp.dto.request;

import com.fangdd.tp.enums.UserActionEnum;

/**
 * @auth ycoe
 * @date 18/12/4
 */
public class LogDto {
    /**
     * 事件类型
     */
    private UserActionEnum action;

    /**
     * 接口
     */
    private WebRestInvokeData invokeRequest;

    public UserActionEnum getAction() {
        return action;
    }

    public void setAction(UserActionEnum action) {
        this.action = action;
    }

    public WebRestInvokeData getInvokeRequest() {
        return invokeRequest;
    }

    public void setInvokeRequest(WebRestInvokeData invokeRequest) {
        this.invokeRequest = invokeRequest;
    }
}
