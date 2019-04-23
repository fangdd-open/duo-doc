package com.fangdd.tp.dto.request;

/**
 * @author wtx
 * @date 18/7/5
 */
public class BasePageReqDto {
    /**
     * 当前分页数，1表示第一页
     *
     * @required
     * @demo 1
     */
    private Integer pageNo = 1;

    /**
     * 每页最大记录数
     *
     * @required
     * @demo 20
     */
    private Integer pageSize = 20;

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
