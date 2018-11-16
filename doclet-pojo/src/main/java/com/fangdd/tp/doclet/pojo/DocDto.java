package com.fangdd.tp.doclet.pojo;


import com.fangdd.tp.doclet.pojo.entity.EnvItem;

import java.util.List;
import java.util.Map;

/**
 * @auth ycoe
 * @date 18/1/23
 */
public class DocDto {
    private Artifact artifact;

    private List<Chapter> chapters;

    private List<Entity> entities;

    private String docletVersion;

    private Map<String, String> markdownMaps;

    private List<EnvItem> envs;

    /**
     * 各环境的restful测试接口
     */
    private String restTest;

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public String getDocletVersion() {
        return docletVersion;
    }

    public void setDocletVersion(String docletVersion) {
        this.docletVersion = docletVersion;
    }

    public Map<String, String> getMarkdownMaps() {
        return markdownMaps;
    }

    public void setMarkdownMaps(Map<String, String> markdownMaps) {
        this.markdownMaps = markdownMaps;
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
