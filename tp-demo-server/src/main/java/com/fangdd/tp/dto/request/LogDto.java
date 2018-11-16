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
    private InvokeData invokeRequest;

    public UserActionEnum getAction() {
        return action;
    }

    public void setAction(UserActionEnum action) {
        this.action = action;
    }

    public InvokeData getInvokeRequest() {
        return invokeRequest;
    }

    public void setInvokeRequest(InvokeData invokeRequest) {
        this.invokeRequest = invokeRequest;
    }
}
