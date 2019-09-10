package com.fangdd.tp.entity;

import java.util.List;

/**
 * @author xuwenzhen
 * @date 19/1/17
 */
public class UserData {
    /**
     * 用户ID
     *
     * @demo 10001
     */
    private Long id;

    /**
     * 用户关注的文档ID
     *
     * @demo ["com.fangdd.cp:m-web-cp-server"]
     */
    private List<String> focusDocIds;

    /**
     * 展开的菜单
     */
    private List<DocExpandingMenu> expandingMenus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getFocusDocIds() {
        return focusDocIds;
    }

    public void setFocusDocIds(List<String> focusDocIds) {
        this.focusDocIds = focusDocIds;
    }

    public List<DocExpandingMenu> getExpandingMenus() {
        return expandingMenus;
    }

    public void setExpandingMenus(List<DocExpandingMenu> expandingMenus) {
        this.expandingMenus = expandingMenus;
    }
}
