package com.fangdd.tp.entity;

import com.fangdd.tp.doclet.pojo.Api;

import java.util.List;

/**
 * @author xuwenzhen
 * @date 19/1/21
 */
public class ApiEntity extends Api {
    /**
     * 主键，与key值一致
     */
    private String id;

    /**
     * 所属站点
     */
    private String site;

    /**
     * 所属文档：{groupId}:{artifactId}
     */
    private String docId;

    /**
     * 所属分类
     */
    private List<String> cates;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public List<String> getCates() {
        return cates;
    }

    public void setCates(List<String> cates) {
        this.cates = cates;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
