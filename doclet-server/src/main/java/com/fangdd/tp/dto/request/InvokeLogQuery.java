package com.fangdd.tp.dto.request;

/**
 * @author xuwenzhen
 * @date 2019/2/26
 */
public class InvokeLogQuery extends BasePageReqDto {
    /**
     * apiKey
     *
     * @demo 18a3262d01e1228774e86afa6e9a8ad0
     */
    private String apiKey;

    /**
     * 接口类型：0=RestFul 1=Dubbo
     *
     * @demo 0
     */
    private Integer type;

    /**
     * 文档ID
     *
     * @required
     * @demo com.fangdd.tp:scanengine-dp-server
     */
    private String docId;

    /**
     * 用户ID
     *
     * @demo 1011
     */
    private Long userId;

    /**
     * 调用环境代码
     *
     * @demo TEST
     */
    private String envCode;

    /**
     * 起始时间
     *
     * @demo 2019-01-12 12:30
     */
    private String startTime;

    /**
     * 结束时间
     *
     * @demo 2019-01-12 13:30
     */
    private String endTime;

    /**
     * 响应状态
     *
     * @demo 200
     */
    private Integer responseStatus;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }
}
