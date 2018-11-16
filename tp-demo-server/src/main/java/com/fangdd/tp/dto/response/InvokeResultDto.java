package com.fangdd.tp.dto.response;

import com.fangdd.tp.dto.request.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @auth ycoe
 * @date 18/11/23
 */
public class InvokeResultDto {
    /**
     * Http响应码
     *
     * @demo 200
     */
    private Integer status;

    /**
     * 响应体
     */
    private String responseBody;

    /**
     * 响应头
     */
    private List<RequestParam> headers;

    /**
     * 整个请求时间，单位：毫秒
     *
     * @demo 100
     */
    private Long responseAtMillis;

    /**
     * 接收响应时间，单位：毫秒
     *
     * @demo 96
     */
    private Long receivedResponseAtMillis;

    /**
     * 发送请求时间，单位：毫秒
     *
     * @demo 3
     */
    private Long sentRequestAtMillis;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Long getResponseAtMillis() {
        return responseAtMillis;
    }

    public void setResponseAtMillis(Long responseAtMillis) {
        this.responseAtMillis = responseAtMillis;
    }

    public Long getReceivedResponseAtMillis() {
        return receivedResponseAtMillis;
    }

    public void setReceivedResponseAtMillis(Long receivedResponseAtMillis) {
        this.receivedResponseAtMillis = receivedResponseAtMillis;
    }

    public Long getSentRequestAtMillis() {
        return sentRequestAtMillis;
    }

    public void setSentRequestAtMillis(Long sentRequestAtMillis) {
        this.sentRequestAtMillis = sentRequestAtMillis;
    }

    public List<RequestParam> getHeaders() {
        return headers;
    }

    public void setHeaders(List<RequestParam> headers) {
        this.headers = headers;
    }
}
