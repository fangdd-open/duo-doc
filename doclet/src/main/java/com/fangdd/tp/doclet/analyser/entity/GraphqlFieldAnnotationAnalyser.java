package com.fangdd.tp.doclet.analyser.entity;

import com.fangdd.tp.doclet.helper.AnnotationHelper;
import com.fangdd.tp.doclet.pojo.EntityRef;
import com.google.common.base.Strings;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.FieldDoc;

/**
 * GraphqlField注解
 *
 * @author xuwenzhen
 * @date 2019/5/29
 */
public class GraphqlFieldAnnotationAnalyser extends EntityFieldAnnotationAnalyser {

    private static final String VALUE = "value";
    private static final String DEPENDENCY = "dependency";
    private static final String METHOD = "method";
    private static final int CLASS_LENGTH = ".class".length();

    /**
     * 解析注解
     *
     * @param annotationDesc 当前属性字段上的注解信息
     * @param fieldRef       当前属性的信息
     * @param fieldDoc       当前属性的javadoc信息
     */
    @Override
    public void analyse(AnnotationDesc annotationDesc, EntityRef fieldRef, FieldDoc fieldDoc) {
        String controller = AnnotationHelper.getStringValue(annotationDesc, VALUE);
        String method = AnnotationHelper.getStringValue(annotationDesc, METHOD);
        String dependency = AnnotationHelper.getStringValues(annotationDesc, DEPENDENCY, ",");
        if (Strings.isNullOrEmpty(controller) || Strings.isNullOrEmpty(method) || Strings.isNullOrEmpty(dependency)) {
            //丢弃
            return;
        }
        controller = controller.substring(0, controller.length() - CLASS_LENGTH);
        fieldRef.setGraphqlField(controller + "." + method + ":" + dependency);
    }
}
