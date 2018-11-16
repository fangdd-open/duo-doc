package com.fangdd.tp.doclet.pojo.entity;

import java.util.List;

/**
 * @auth ycoe
 * @date 18/10/26
 */
public class Env {
    /**
     * 项目ID：{groupId}:{artifactId}
     */
    private String id;

    /**
     * 测试URL，用于检测链接是否可用，仅支持GET请求
     */
    private String restTest;

    /**
     * 环境列表
     */
    private List<EnvItem> envs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<EnvItem> getEnvs() {
        return envs;
    }

    public void setEnvs(List<EnvItem> envs) {
        this.envs = envs;
    }

    public String getRestTest() {
        return restTest;
    }

    public void setRestTest(String restTest) {
        this.restTest = restTest;
    }
}
