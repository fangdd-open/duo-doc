package com.fangdd.tp.doclet.pojo;

/**
 * @author xuwenzhen
 * @date 18/1/24
 */
public class MongoDbEntity {
    /**
     * UUID
     * MongoDB属性
     */
    private String id;

    /**
     * doc._id
     * MongoDB属性
     */
    private String docId;

    /**
     * 版本号，直接使用更新时间即可
     * MongoDB属性
     */
    private Long docVersion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Long getDocVersion() {
        return docVersion;
    }

    public void setDocVersion(Long docVersion) {
        this.docVersion = docVersion;
    }
}
