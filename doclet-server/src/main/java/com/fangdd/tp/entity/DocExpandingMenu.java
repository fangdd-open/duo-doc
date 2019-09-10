package com.fangdd.tp.entity;

/**
 * @author xuwenzhen
 * @date 19/1/17
 */
public class DocExpandingMenu {
    /**
     * 文档ID
     * @demo com.fangdd.cp:m-web-cp-server
     */
    private String docId;

    /**
     * 展开状态的菜单
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
