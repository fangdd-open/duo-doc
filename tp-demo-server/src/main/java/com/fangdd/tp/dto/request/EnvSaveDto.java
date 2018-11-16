package com.fangdd.tp.dto.request;

import com.fangdd.tp.doclet.pojo.entity.EnvItem;

import java.util.List;

/**
 * @auth ycoe
 * @date 18/10/26
 */
public class EnvSaveDto {
    /**
     * 项目ID：{groupId}:{artifactId}
     */
    private String id;

    /**
     * 各环境
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
