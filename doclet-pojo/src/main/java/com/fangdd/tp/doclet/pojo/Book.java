package com.fangdd.tp.doclet.pojo;

import java.util.Set;

/**
 * @author xuwenzhen
 * @date 18/1/20
 */
public class Book {
    private Set<Chapter> chapterSet;

    private Set<Entity> entitySet;

    public Set<Chapter> getChapterSet() {
        return chapterSet;
    }

    public void setChapterSet(Set<Chapter> chapterSet) {
        this.chapterSet = chapterSet;
    }

    public Set<Entity> getEntitySet() {
        return entitySet;
    }

    public void setEntitySet(Set<Entity> entitySet) {
        this.entitySet = entitySet;
    }
}
