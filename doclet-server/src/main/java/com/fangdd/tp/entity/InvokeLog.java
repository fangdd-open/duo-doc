package com.fangdd.tp.entity;

import com.fangdd.traffic.common.mongo.annotation.Ignore;

/**
 * @author xuwenzhen
 * @date 2019/2/26
 */
public class InvokeLog {
    /**
     * 主键
     * @demo 5c754878e5dc0c4dbfbc30ba
     */
    private String id;

    /**
     * 文档ID
     *
     * @demo com.fangdd.tp:scanengine-dp-server
     */
    private String docId;

    /**
     * api key
     *
     * @demo 70089e509f21760d2495efbf3ac1f5f8
     */
    private String apiKey;

    /**
     * 接口类型：0=RestFul, 1=Dubbo
     */
    private Integer type;

    /**
     * 所属站点
     *
     * @demo fdd
     */
    private String site;

    /**
     * 接口名称
     *
     * @demo 取消订阅信息流接口
     */
    private String apiName;

    /**
     * 环境代码
     *
     * @demo Test
     */
    private String envCode;

    /**
     * 创建的用户ID
     *
     * @demo 123456
     */
    private Long userId;

    /**
     * RestFul请求的内容
     */
    private InvokeLogRestReq restReq;

    /**
     * Dubbo请求的内容
     */
    private InvokeLogDubboReq dubboReq;

    /**
     * 响应码, 200表示成功
     *
     * @demo 200
     */
    private Integer status;

    /**
     * 整个请求时间，单位：毫秒
     *
     * @demo 100
     */
    private Long responseAtMillis;

    /**
     * RestFul请求响应
     */
    private InvokeLogRestResp restResp;

    /**
     * Dubbo请求响应
     */
    private InvokeLogDubboResp dubboResp;

    /**
     * 调用时间
     *
     * @demo 1519975108000
     */
    private Long invokeTime;

    /**
     * 用户名，数据不入库
     */
    @Ignore
    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public InvokeLogRestReq getRestReq() {
        return restReq;
    }

    public void setRestReq(InvokeLogRestReq restReq) {
        this.restReq = restReq;
    }

    public InvokeLogDubboReq getDubboReq() {
        return dubboReq;
    }

    public void setDubboReq(InvokeLogDubboReq dubboReq) {
        this.dubboReq = dubboReq;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getResponseAtMillis() {
        return responseAtMillis;
    }

    public void setResponseAtMillis(Long responseAtMillis) {
        this.responseAtMillis = responseAtMillis;
    }

    public InvokeLogRestResp getRestResp() {
        return restResp;
    }

    public void setRestResp(InvokeLogRestResp restResp) {
        this.restResp = restResp;
    }

    public InvokeLogDubboResp getDubboResp() {
        return dubboResp;
    }

    public void setDubboResp(InvokeLogDubboResp dubboResp) {
        this.dubboResp = dubboResp;
    }

    public Long getInvokeTime() {
        return invokeTime;
    }

    public void setInvokeTime(Long invokeTime) {
        this.invokeTime = invokeTime;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
