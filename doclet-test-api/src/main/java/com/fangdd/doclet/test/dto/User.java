package com.fangdd.doclet.test.dto;

/**
 * @author xuwenzhen
 * @date 19/1/4
 */
public class User {
    /**
     * 用户ID
     *
     * @demo 123
     */
    private Integer id;

    /**
     * 用户名称
     *
     * @demo 张三
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
