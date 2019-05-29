package com.fangdd.tp.doclet.analyser.entity;

import com.fangdd.tp.doclet.pojo.EntityRef;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.FieldDoc;

/**
 * GraphqlField注解
 * @author xuwenzhen
 * @date 2019/5/29
 */
public class GraphqlFieldAnnotationAnalyser extends EntityFieldAnnotationAnalyser {

    private static final String VALUE = "value";

    /**
     * 解析注解
     *
     * @param annotationDesc 当前属性字段上的注解信息
     * @param fieldRef       当前属性的信息
     * @param fieldDoc       当前属性的javadoc信息
     */
    @Override
    public void analyse(AnnotationDesc annotationDesc, EntityRef fieldRef, FieldDoc fieldDoc) {
        AnnotationDesc.ElementValuePair[] fieldValues = annotationDesc.elementValues();
        String controller = null;
        String method = null;
        for (AnnotationDesc.ElementValuePair kv : fieldValues) {
            String name = kv.element().name();
            String value = kv.value().toString();
            if (VALUE.equals(name)) {
                controller = value.substring(0, value.length() - ".class".length());
            } else {
                method = value.replaceAll("\"", "");
            }
        }
        if (controller == null || method == null) {
            //丢弃
        }
        fieldRef.setGraphqlField(controller + "." + method);
    }
}
