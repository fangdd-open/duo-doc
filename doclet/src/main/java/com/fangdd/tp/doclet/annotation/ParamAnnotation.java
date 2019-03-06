package com.fangdd.tp.doclet.annotation;

import com.fangdd.tp.doclet.pojo.EntityRef;
import com.sun.javadoc.AnnotationDesc;

/**
 * @author xuwenzhen
 * @date 2019/3/4
 */
public interface ParamAnnotation {
    /**
     * 获取注解
     *
     * @return 注解类全名
     * @demo org.springframework.web.bind.annotation.RequestParam
     */
    String getAnnotationFullClass();

    /**
     * 在文档上显示的注解
     *
     * @return 在文档上显示的注解名称
     * @demo @RequestParam
     */
    String getDocAnnotation();

    /**
     * 分析参数注解
     *
     * @param param          当前参数
     * @param annotationDesc 注解
     */
    boolean analyse(EntityRef param, AnnotationDesc annotationDesc);
}
