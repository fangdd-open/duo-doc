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
    private static final String CLASS_STR = ".class";
    private static final String CONTROLLER = "controller";
    private static final String JOINER = ",";
    private static final String NAME = "name";

    /**
     * 解析注解
     *
     * @param annotationDesc 当前属性字段上的注解信息
     * @param fieldRef       当前属性的信息
     * @param fieldDoc       当前属性的javadoc信息
     */
    @Override
    public boolean analyse(AnnotationDesc annotationDesc, EntityRef fieldRef, FieldDoc fieldDoc) {
        String providerName = AnnotationHelper.getStringValue(annotationDesc, NAME);
        String dependency = AnnotationHelper.getStringValues(annotationDesc, DEPENDENCY, JOINER);
        if (!Strings.isNullOrEmpty(providerName)) {
            fieldRef.setGraphqlField("@" + providerName + ":" + dependency);
            return true;
        }

        String controllerClazz = AnnotationHelper.getStringValue(annotationDesc, VALUE);
        String controllerStr = AnnotationHelper.getStringValue(annotationDesc, CONTROLLER);
        String method = AnnotationHelper.getStringValue(annotationDesc, METHOD);
        if (Strings.isNullOrEmpty(controllerClazz) && !Strings.isNullOrEmpty(controllerStr)) {
            controllerClazz = controllerStr;
        }
        if (Strings.isNullOrEmpty(controllerClazz) || Strings.isNullOrEmpty(method) || Strings.isNullOrEmpty(dependency)) {
            //丢弃
            return true;
        }
        if (controllerClazz.endsWith(CLASS_STR)) {
            controllerClazz = controllerClazz.substring(0, controllerClazz.length() - CLASS_STR.length());
        }

        String graphqlField = controllerClazz + "." + method + ":" + dependency;
        fieldRef.setGraphqlField(graphqlField);
        return true;
    }
}
