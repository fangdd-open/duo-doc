package com.fangdd.tp.entity;

import com.fangdd.tp.dto.request.WebRestInvokeData;

/**
 * @auth ycoe
 * @date 18/12/4
 */
public class UserLog {
    /**
     * 日志ID
     *
     * @demo iJKV1QiLCJhbGciOiJ
     */
    private String id;

    /**
     * 团队代码
     */
    private String site;

    /**
     * 用户ID
     *
     * @demo 1002
     */
    private Long userId;

    /**
     * 事件
     *
     * @demo 1
     */
    private Integer action;

    /**
     * 操作的apiId
     */
    private String apiId;

    /**
     * 标题
     *
     * @demo 保存接口参数
     */
    private String title;

    /**
     * RestFul接口调用参数
     */
    private WebRestInvokeData request;

    /**
     * Dubbo接口调用参数
     */
    private ApiRequestDubbo requestDubbo;

    /**
     * 事件时间
     */
    private Long actionTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public WebRestInvokeData getRequest() {
        return request;
    }

    public void setRequest(WebRestInvokeData request) {
        this.request = request;
    }

    public Long getActionTime() {
        return actionTime;
    }

    public void setActionTime(Long actionTime) {
        this.actionTime = actionTime;
    }

    public ApiRequestDubbo getRequestDubbo() {
        return requestDubbo;
    }

    public void setRequestDubbo(ApiRequestDubbo requestDubbo) {
        this.requestDubbo = requestDubbo;
    }
}
