package com.fangdd.tp.doclet.pojo;

/**
 * @auth ycoe
 * @date 18/1/13
 */
public class EntityRef {
    /**
     * entity.name
     */
    private String entityName;

    /**
     * 字段名称
     */
    private String name;

    /**
     * 注释
     */
    private String comment;

    /**
     * 是否必填
     */
    private boolean required = false;

    /**
     * 默认值
     */
    private String demo;

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setDemo(String demo) {
        this.demo = demo;
    }

    public String getDemo() {
        return demo;
    }
}
