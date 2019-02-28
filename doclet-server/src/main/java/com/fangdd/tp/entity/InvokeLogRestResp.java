package com.fangdd.tp.entity;

import com.fangdd.tp.doclet.pojo.entity.RequestParam;

import java.util.List;

/**
 *
 * @author xuwenzhen
 * @date 2019/2/26
 */
public class InvokeLogRestResp {
    /**
     * 响应体
     *
     * @demo {"code": 200, "data": 12}
     */
    private String responseBody;

    /**
     * 响应头
     */
    private List<RequestParam> headers;

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public List<RequestParam> getHeaders() {
        return headers;
    }

    public void setHeaders(List<RequestParam> headers) {
        this.headers = headers;
    }
}
