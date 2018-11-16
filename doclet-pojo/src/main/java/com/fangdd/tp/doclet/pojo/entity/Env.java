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
}
