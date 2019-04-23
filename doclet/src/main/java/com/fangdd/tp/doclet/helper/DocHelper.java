package com.fangdd.tp.doclet.helper;

import com.fangdd.tp.doclet.DocletConfig;
import com.fangdd.tp.doclet.annotation.ParamAnnotation;
import com.fangdd.tp.doclet.annotation.param.*;
import com.fangdd.tp.doclet.constant.SpringMvcConstant;
import com.fangdd.tp.doclet.exporter.Exporter;
import com.fangdd.tp.doclet.exporter.impl.ConsoleMarkdownExporter;
import com.fangdd.tp.doclet.exporter.impl.ServerExporter;

import java.util.HashMap;
import java.util.Map;

/**
 * 本Helper方法提供了各种自定义的配置
 *
 * @author xuwenzhen
 * @date 2019/3/4
 */
public class DocHelper {
    /**
     * RestFul接口方法定义
     */
    public static final String[][] METHOD_MAPPINGS = new String[][]{
            new String[]{SpringMvcConstant.ANNOTATION_REQUEST_MAPPING, ""},
            new String[]{SpringMvcConstant.ANNOTATION_GET_MAPPING, "GET"},
            new String[]{SpringMvcConstant.ANNOTATION_POST_MAPPING, "POST"},
            new String[]{SpringMvcConstant.ANNOTATION_PUT_MAPPING, "PUT"},
            new String[]{SpringMvcConstant.ANNOTATION_PATCH_MAPPING, "PATCH"},
            new String[]{SpringMvcConstant.ANNOTATION_DELETE_MAPPING, "DELETE"},
            new String[]{SpringMvcConstant.ANNOTATION_POST_MAPPING, "POST"}
    };

    private static final Map<String, Exporter> EXPORTER_MAP = new HashMap<String, Exporter>();

    /**
     * RestFul接口方法定义中参数的注解处理类
     */
    private static final Map<String, ParamAnnotation> restApiParamAnnotationMap = new HashMap<String, ParamAnnotation>();

    static {
        RequestParamAnnotation paramAnnotation = new RequestParamAnnotation();
        //注册默认的处理器
        restApiParamAnnotationMap.put(null, paramAnnotation);

        registRestApiParamAnnotation(paramAnnotation);
        registRestApiParamAnnotation(new PathVariableAnnotation());
        registRestApiParamAnnotation(new RequestAttributeAnnotation());
        registRestApiParamAnnotation(new RequestBodyAnnotation());
        registRestApiParamAnnotation(new RequestHeaderAnnotation());

        //注册导出器
        registExporter(new ConsoleMarkdownExporter());
        registExporter(new ServerExporter());
    }

    public static ParamAnnotation getRestApiParamAnnotation(String annotationStr) {
        return restApiParamAnnotationMap.get(annotationStr);
    }

    public static ParamAnnotation registRestApiParamAnnotation(ParamAnnotation paramAnnotation) {
        return restApiParamAnnotationMap.put(paramAnnotation.getAnnotationFullClass(), paramAnnotation);
    }

    public static void registExporter(Exporter exporter) {
        EXPORTER_MAP.put(exporter.exporterName(), exporter);
    }

    public static Exporter getExporter() {
        String exporter = DocletConfig.exporter;
        return EXPORTER_MAP.get(exporter);
    }
}