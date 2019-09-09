package com.fangdd.tp.doclet.helper;

import com.fangdd.tp.doclet.annotation.ParamAnnotation;
import com.fangdd.tp.doclet.annotation.param.*;
import com.fangdd.tp.doclet.constant.SpringMvcConstant;
import com.fangdd.tp.doclet.exporter.Exporter;
import com.fangdd.tp.doclet.exporter.impl.ApiJson4GraphQLExporter;
import com.fangdd.tp.doclet.exporter.impl.ApiJsonExporter;
import com.fangdd.tp.doclet.exporter.impl.ConsoleMarkdownExporter;
import com.fangdd.tp.doclet.exporter.impl.ServerExporter;
import com.fangdd.tp.doclet.pojo.Chapter;
import com.google.common.base.Splitter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fangdd.tp.doclet.DocletConfig.exporter;

/**
 * 本Helper方法提供了各种自定义的配置
 *
 * @author xuwenzhen
 * @date 2019/3/4
 */
public class DocHelper {
    private static final Logger logger = new Logger();
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
        registExporter(new ApiJson4GraphQLExporter());
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

    /**
     * 导出文档
     *
     * @param chapterSet chapterSet
     */
    public static void export(final List<Chapter> chapterSet) {
        String exporters = exporter;
        Iterable<String> iterable = Splitter
                .on(",")
                .omitEmptyStrings()
                .trimResults()
                .split(exporters);
        for (String exporterName : iterable) {
            Exporter existsExporter = EXPORTER_MAP.get(exporterName);
            if (existsExporter != null) {
                existsExporter.export(chapterSet);
            } else {
                logger.info("未指定或无法找到对应的导出方式：exporter=" + exporterName);
            }
        }
    }
}
