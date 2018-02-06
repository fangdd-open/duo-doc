package com.fangdd.tp.doclet.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class Section {

    /**
     * 章节名称
     */
    private String name;

    /**
     * 全局唯一的一个代码，可以使用类全名
     */
    private String code;

    /**
     * 注释
     */
    private String comment;

    /**
     * 排序,越大越前
     */
    private Integer rank;

    /**
     * 章节内的接口
     */
    private List<Api> apis;

    @JSONField(deserialize = false, serialize = false)
    private Chapter chapter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public List<Api> getApis() {
        return apis;
    }

    public void setApis(List<Api> apis) {
        this.apis = apis;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
}
