package com.fangdd.tp.doclet.pojo;


import java.util.List;

/**
 * @auth ycoe
 * @date 18/1/23
 */
public class DocDto {
    private Artifact artifact;

    private List<Chapter> chapters;

    private List<Entity> entities;

    private String docletVersion;

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
}
