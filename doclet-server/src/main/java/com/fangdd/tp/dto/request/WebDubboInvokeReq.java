package com.fangdd.tp.dto.request;

import java.util.List;
import java.util.Map;

/**
 * 从web前端发起的dubbo请求
 *
 * @auth ycoe
 * @date 19/1/24
 */
public class WebDubboInvokeReq {
    /**
     * 用于保存参数时使用
     */
    private String id;

    /**
     * 用于保存参数时使用
     */
    private String name;

    /**
     * 文档ID
     *
     * @demo com.fangdd.tp:scanengine-dp-server
     */
    private String docId;

    /**
     * api key
     *
     * @demo
     */
    private String apiKey;

    /**
     * 环境代码
     *
     * @demo Test
     */
    private String envCode;

    /**
     * 调用方法参数值
     */
    private List<Map<String, Object>> params;

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

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public List<Map<String, Object>> getParams() {
        return params;
    }

    public void setParams(List<Map<String, Object>> params) {
        this.params = params;
    }
}
