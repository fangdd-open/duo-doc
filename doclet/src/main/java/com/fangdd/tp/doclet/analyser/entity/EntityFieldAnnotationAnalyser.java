package com.fangdd.tp.doclet.analyser.entity;

import com.fangdd.tp.doclet.pojo.EntityRef;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.FieldDoc;

/**
 * @author ycoe
 * @date 18/2/11
 */
public abstract class EntityFieldAnnotationAnalyser {
    /**
     * 检查是否需要执行analyse()
     *
     * @param fieldRef
     * @param fieldDoc
     * @return
     */
    protected boolean check(EntityRef fieldRef, FieldDoc fieldDoc) {
        return true;
    }

    /**
     * 解析注解
     * @param annotationDesc 当前属性字段上的注解信息
     * @param fieldRef 当前属性的信息
     * @param fieldDoc 当前属性的javadoc信息
     */
    public abstract void analyse(AnnotationDesc annotationDesc, EntityRef fieldRef, FieldDoc fieldDoc);
}
