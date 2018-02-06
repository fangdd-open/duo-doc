package com.fangdd.tp.dto;

/**
 * 管理员
 * @auth ycoe
 * @date 18/1/13
 */
public class Admin {
    /**
     * 是否删除
     * @demo false
     * {@value "false"}
     */
    private boolean deleted;

    /**
     * 级别
     * @demo 1
     */
    private Integer level = 12233;

    private Integer status;

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
