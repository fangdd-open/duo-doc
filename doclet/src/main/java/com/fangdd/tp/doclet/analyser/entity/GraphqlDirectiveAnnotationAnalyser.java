package com.fangdd.tp.doclet.analyser.entity;

import com.fangdd.tp.doclet.helper.AnnotationHelper;
import com.fangdd.tp.doclet.pojo.EntityRef;
import com.google.common.base.Strings;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.FieldDoc;

/**
 * GraphqlDirective注解
 *
 * @author xuwenzhen
 * @date 2019/7/12
 */
public class GraphqlDirectiveAnnotationAnalyser extends EntityFieldAnnotationAnalyser {
    private static final String VALUE = "value";
    private static final String NAME_SPLITTER = ",";

    /**
     * 解析注解
     *
     * @param annotationDesc 当前属性字段上的注解信息
     * @param fieldRef       当前属性的信息
     * @param fieldDoc       当前属性的javadoc信息
     */
    @Override
    public void analyse(AnnotationDesc annotationDesc, EntityRef fieldRef, FieldDoc fieldDoc) {
        String directiveName = AnnotationHelper.getStringValues(annotationDesc, VALUE, NAME_SPLITTER);
        if (Strings.isNullOrEmpty(directiveName)) {
            return;
        }

        String directives = fieldRef.getGraphqlDirective();
        if (Strings.isNullOrEmpty(directives)) {
            directives = directiveName;
        } else {
            directives = NAME_SPLITTER + directiveName;
        }
        fieldRef.setGraphqlField(directives);
    }
}
