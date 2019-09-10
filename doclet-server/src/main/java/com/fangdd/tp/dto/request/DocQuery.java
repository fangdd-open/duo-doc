package com.fangdd.tp.dto.request;

/**
 * @author xuwenzhen
 * @date 18/1/23
 */
public class DocQuery {
    private String keyword;

    private Integer pageNo = 1;

    private Integer pageSize = 200;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
