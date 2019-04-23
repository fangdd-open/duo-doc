package com.fangdd.tp.entity;

import java.util.List;

/**
 * RestFul请求的请求内容
 * Created by xuwenzhen on 2019/2/26.
 */
public class InvokeLogDubboReq {
    /**
     * 调用方法参数值
     */
    private List<ApiRequestDubboParamItem> params;

    public List<ApiRequestDubboParamItem> getParams() {
        return params;
    }

    public void setParams(List<ApiRequestDubboParamItem> params) {
        this.params = params;
    }
}
