package com.fangdd.tp.dto.request;

/**
 * @auth ycoe
 * @date 19/1/17
 */
public class ExpandData {
    /**
     * 文档ID
     *
     * @required
     * @demo com.fangdd.cp:m-web-cp-server
     */
    private String docId;

    /**
     * 展开的菜单配置
     *
     * @demo 小区接口|小区接口.小区关注接口
     */
    private String expandingMenus;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getExpandingMenus() {
        return expandingMenus;
    }

    public void setExpandingMenus(String expandingMenus) {
        this.expandingMenus = expandingMenus;
    }
}
