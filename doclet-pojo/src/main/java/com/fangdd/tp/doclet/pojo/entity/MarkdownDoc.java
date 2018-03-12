package com.fangdd.tp.doclet.pojo.entity;

import com.fangdd.tp.doclet.pojo.MongoDbEntity;

import java.io.Serializable;

/**
 * @auth ycoe
 * @date 18/3/12
 */
public class MarkdownDoc extends MongoDbEntity implements Serializable {
    /**
     * 文档名称
     */
    private String name;

    /**
     * markdown
     */
    private String markdown;

    /**
     * markdown生成的html，只生成一次
     */
    private String html;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
