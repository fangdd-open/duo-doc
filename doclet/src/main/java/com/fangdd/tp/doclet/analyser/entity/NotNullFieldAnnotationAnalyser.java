package com.fangdd.tp.doclet.analyser.entity;

import com.fangdd.tp.doclet.pojo.EntityRef;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.FieldDoc;

/**
 * @author xuwenzhen
 * @date 18/2/11
 */
public class NotNullFieldAnnotationAnalyser extends EntityFieldAnnotationAnalyser {
    @Override
    public boolean analyse(AnnotationDesc annotationDesc, EntityRef fieldRef, FieldDoc fieldDoc) {
        //被标注了  @Null
        fieldRef.setRequired(true);
        return true;
    }
}
