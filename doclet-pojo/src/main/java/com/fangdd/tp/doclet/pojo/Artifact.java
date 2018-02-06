package com.fangdd.tp.doclet.pojo;

/**
 * @auth ycoe
 * @date 18/1/10
 */
public class Artifact {
    /**
     * 使用 服务坐标，不带版本号，比如：com.fangdd:tp-demo-server
     */
    private String id;

    /**
     * 最新的文档版本号
     */
    private Long docVersion;

    /**
     * artifactId
     */
    private String artifactId;

    /**
     * version
     */
    private String version;

    /**
     * groupId
     */
    private String groupId;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目描述
     */
    private String description;

    /**
     * md5，用户计算是否一致
     */
    private String md5;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDocVersion() {
        return docVersion;
    }

    public void setDocVersion(Long docVersion) {
        this.docVersion = docVersion;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
