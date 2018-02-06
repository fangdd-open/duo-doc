package com.fangdd.tp.dto;

/**
 * @auth ycoe
 * @date 18/1/15
 */
public class UserFilter {
    /**
     * 用户ID，多个使用逗号分隔
     *
     * @demo 123, 345
     * @required true
     */
    private String ids;

    /**
     * 对用户名模糊搜索的关键词
     *
     * @demo 张|三
     */
    private String keyword;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
