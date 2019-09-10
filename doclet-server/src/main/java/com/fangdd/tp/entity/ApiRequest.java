package com.fangdd.tp.entity;

import com.fangdd.tp.dto.request.RequestBodyParam;
import com.fangdd.tp.doclet.pojo.entity.RequestParam;

import java.util.List;

/**
 * @author xuwenzhen
 * @date 18/11/23
 */
public class ApiRequest {
    /**
     * 接口ID
     *
     * @required
     * @demo 1b1089f5911fee17b3780e56b6d4eb0f
     */
    private String id;
    /**
     * api key
     * @demo
     */
    private String apiKey;

    /**
     * 保存名称
     *
     * @demo 测试数据
     */
    private String name;

    /**
     * 是否公开接口调用参数
     *
     * @demo false
     */
    private Boolean publicState;

    /**
     * 用户ID
     *
     * @demo 10001
     */
    private Long userId;

    /**
     * 用户名称
     *
     * @demo 张三
     */
    private String userName;

    /**
     * 环境代码
     *
     * @demo dev
     */
    private String envCode;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPublicState() {
        return publicState;
    }

    public void setPublicState(Boolean publicState) {
        this.publicState = publicState;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

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
