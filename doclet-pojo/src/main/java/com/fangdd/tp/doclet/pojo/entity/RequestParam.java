package com.fangdd.tp.doclet.pojo.entity;

/**
 * @author xuwenzhen
 * @date 18/11/23
 */
public class RequestParam {
    /**
     * 键名
     *
     * @demo name
     */
    private String key;

    /**
     * 键值
     *
     * @demo ycoe
     */
    private String value;

    /**
     * 描述
     *
     * @demo 名称
     */
    private String description;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
