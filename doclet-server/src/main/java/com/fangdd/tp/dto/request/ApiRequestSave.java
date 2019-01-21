package com.fangdd.tp.dto.request;

/**
 * @auth ycoe
 * @date 18/12/4
 */
public class ApiRequestSave extends InvokeData {
    /**
     * api_request._id
     *
     * @demo 5c0643f0fef286701a21729b
     */
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
