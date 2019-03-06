package com.fangdd.tp.doclet.annotation.param;

import com.fangdd.tp.doclet.annotation.ParamAnnotation;
import com.fangdd.tp.doclet.helper.AnnotationHelper;
import com.fangdd.tp.doclet.pojo.EntityRef;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationValue;

/**
 * @author xuwenzhen
 * @date 2019/3/4
 */
public class RequestParamAnnotation implements ParamAnnotation {
    /**
     * 获取注解
     *
     * @return 注解类全名
     * @demo org.springframework.web.bind.annotation.RequestParam
     */
    @Override
    public String getAnnotationFullClass() {
        return "org.springframework.web.bind.annotation.RequestParam";
    }

    /**
     * 在文档上显示的注解
     *
     * @return 在文档上显示的注解名称
     * @demo @RequestParam
     */
    @Override
    public String getDocAnnotation() {
        return "@RequestParam";
    }

    /**
     * 分析参数注解
     *  @param param          当前参数
     * @param annotationDesc 注解
     */
    @Override
    public boolean analyse(EntityRef param, AnnotationDesc annotationDesc) {
        Boolean required = true;
        String defaultValue = null;

        if (annotationDesc != null) {
            AnnotationValue requiredVal = AnnotationHelper.getValue(annotationDesc, "required");
            if (requiredVal != null) {
                required = (Boolean) requiredVal.value();
            }
            defaultValue = AnnotationHelper.getStringValue(annotationDesc, "defaultValue");
        }
        param.setRequired(required);
        param.setDemo(defaultValue);
        param.setDefaultValue(defaultValue);
        param.setAnnotation(getDocAnnotation());
        return true;
    }
}
