package com.fangdd.tp.dto.request;

/**
 * @author ycoe
 * @date 19/1/22
 */
public class DocCreateDto {
    /**
     * 站点ID
     */
    private String site;

    /**
     * 文档ID
     */
    private String docId;

    /**
     * 文档版本号
     */
    private Long docVersion;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
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
