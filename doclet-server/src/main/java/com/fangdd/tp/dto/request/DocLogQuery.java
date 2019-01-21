package com.fangdd.tp.dto.request;

/**
 * @auth ycoe
 * @date 18/1/23
 */
public class DocLogQuery {
    private String docId;

    private Integer pageNo;

    private Integer pageSize;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
