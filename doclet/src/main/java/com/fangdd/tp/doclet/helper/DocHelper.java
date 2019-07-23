package com.fangdd.tp.doclet.helper;

import com.fangdd.tp.doclet.DocletConfig;
import com.fangdd.tp.doclet.annotation.ParamAnnotation;
import com.fangdd.tp.doclet.annotation.param.*;
import com.fangdd.tp.doclet.constant.SpringMvcConstant;
import com.fangdd.tp.doclet.exporter.Exporter;
import com.fangdd.tp.doclet.exporter.impl.ApiJsonExporter;
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
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_PATCH = "PATCH";
    private static final String METHOD_DELETE = "DELETE";

    private static final String EMPTY_STRING = "";
    /**
     * RestFul接口方法定义
     */
    public static final String[][] METHOD_MAPPINGS = new String[][]{
            new String[]{SpringMvcConstant.ANNOTATION_REQUEST_MAPPING, EMPTY_STRING},
            new String[]{SpringMvcConstant.ANNOTATION_GET_MAPPING, METHOD_GET},
            new String[]{SpringMvcConstant.ANNOTATION_POST_MAPPING, METHOD_POST},
            new String[]{SpringMvcConstant.ANNOTATION_PUT_MAPPING, METHOD_PUT},
            new String[]{SpringMvcConstant.ANNOTATION_PATCH_MAPPING, METHOD_PATCH},
            new String[]{SpringMvcConstant.ANNOTATION_DELETE_MAPPING, METHOD_DELETE}
    };

    private static final Map<String, Exporter> EXPORTER_MAP = new HashMap<String, Exporter>();

    /**
     * RestFul接口方法定义中参数的注解处理类
     */
    private static final Map<String, ParamAnnotation> REST_API_PARAM_ANNOTATION_MAP = new HashMap<String, ParamAnnotation>(16);

    static {
        RequestParamAnnotation paramAnnotation = new RequestParamAnnotation();
        //注册默认的处理器
        REST_API_PARAM_ANNOTATION_MAP.put(null, paramAnnotation);

        registRestApiParamAnnotation(paramAnnotation);
        registRestApiParamAnnotation(new PathVariableAnnotation());
        registRestApiParamAnnotation(new RequestAttributeAnnotation());
        registRestApiParamAnnotation(new RequestBodyAnnotation());
        registRestApiParamAnnotation(new RequestHeaderAnnotation());

        //注册导出器
        registExporter(new ConsoleMarkdownExporter());
        registExporter(new ServerExporter());
        registExporter(new ApiJsonExporter());
    }

    public static ParamAnnotation getRestApiParamAnnotation(String annotationStr) {
        return REST_API_PARAM_ANNOTATION_MAP.get(annotationStr);
    }

    public static ParamAnnotation registRestApiParamAnnotation(ParamAnnotation paramAnnotation) {
        return REST_API_PARAM_ANNOTATION_MAP.put(paramAnnotation.getAnnotationFullClass(), paramAnnotation);
    }

    public static void registExporter(Exporter exporter) {
        EXPORTER_MAP.put(exporter.exporterName(), exporter);
    }

    public static Exporter getExporter() {
        String exporter = DocletConfig.exporter;
        return EXPORTER_MAP.get(exporter);
    }
}
