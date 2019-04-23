package com.fangdd.tp.entity;

import com.fangdd.tp.doclet.pojo.entity.RequestParam;
import com.fangdd.tp.dto.request.RequestBodyParam;

import java.util.List;

/**
 * RestFul请求的请求内容
 *
 * @author xuwenzhen
 * @date 2019/2/26
 */
public class InvokeLogRestReq {
    /**
     * 调用URL（不包含参数）
     *
     * @demo http://127.0.0.1:17028/menu/m-index/1337
     */
    private String url;

    /**
     * 调用方法
     *
     * @demo POST
     */
    private String method;

    /**
     * 路径参数
     */
    private List<RequestParam> pathParams;

    /**
     * URL参数
     */
    private List<RequestParam> params;

    /**
     * 请求头
     */
    private List<RequestParam> headers;

    /**
     * POST提交数据
     */
    private RequestBodyParam requestBody;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<RequestParam> getPathParams() {
        return pathParams;
    }

    public void setPathParams(List<RequestParam> pathParams) {
        this.pathParams = pathParams;
    }

    public List<RequestParam> getParams() {
        return params;
    }

    public void setParams(List<RequestParam> params) {
        this.params = params;
    }

    public List<RequestParam> getHeaders() {
        return headers;
    }

    public void setHeaders(List<RequestParam> headers) {
        this.headers = headers;
    }

    public RequestBodyParam getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBodyParam requestBody) {
        this.requestBody = requestBody;
    }
}
