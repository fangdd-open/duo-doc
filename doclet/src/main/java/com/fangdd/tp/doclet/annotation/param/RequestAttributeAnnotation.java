package com.fangdd.tp.doclet.annotation.param;

import com.fangdd.tp.doclet.annotation.ParamAnnotation;
import com.fangdd.tp.doclet.pojo.EntityRef;
import com.sun.javadoc.AnnotationDesc;

/**
 *
 * @author xuwenzhen
 * @date 2019/3/4
 */
public class RequestAttributeAnnotation implements ParamAnnotation {
    /**
     * 获取注解
     *
     * @return 注解类全名
     * @demo org.springframework.web.bind.annotation.RequestParam
     */
    @Override
    public String getAnnotationFullClass() {
        return "org.springframework.web.bind.annotation.RequestAttribute";
    }

    /**
     * 在文档上显示的注解
     *
     * @return 在文档上显示的注解名称
     * @demo @RequestParam
     */
    @Override
    public String getDocAnnotation() {
        return "@RequestAttribute";
    }

    /**
     * 分析参数注解
     *  @param param          当前参数
     * @param annotationDesc 注解
     */
    @Override
    public boolean analyse(EntityRef param, AnnotationDesc annotationDesc) {
        param.setRequired(true);
        param.setAnnotation(getDocAnnotation());
        return false;
    }
}
