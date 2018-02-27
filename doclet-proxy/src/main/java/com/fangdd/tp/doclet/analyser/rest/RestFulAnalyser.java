package com.fangdd.tp.doclet.analyser.rest;

import com.fangdd.tp.doclet.constant.DocletConstant;
import com.fangdd.tp.doclet.constant.SpringMvcConstant;
import com.fangdd.tp.doclet.enums.ApiTypeEnum;
import com.fangdd.tp.doclet.helper.*;
import com.fangdd.tp.doclet.pojo.Api;
import com.fangdd.tp.doclet.pojo.Chapter;
import com.fangdd.tp.doclet.pojo.EntityRef;
import com.fangdd.tp.doclet.pojo.Section;
import com.google.common.collect.Lists;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Tag;

import java.util.List;

/**
 * @auth ycoe
 * @date 18/1/9
 */
public class RestFulAnalyser {

    public static void analyse(ClassDoc classDoc) {
        //@RequestMapping
        AnnotationDesc requestMappingAnnotation = AnnotationHelper.getAnnotation(classDoc.annotations(), SpringMvcConstant.ANNOTATION_REQUEST_MAPPING);
        AnnotationDesc.ElementValuePair[] vs = requestMappingAnnotation.elementValues();
        List<String> basePaths = RequestMappingAnnotationHelper.getRequestMappingAnnotationValues(vs, "value");
        if (basePaths.isEmpty()) {
            basePaths.add("");
        }

        Tag[] tags = classDoc.tags();
        String chapterName = TagHelper.getStringValue(tags, "@chapter", DocletConstant.DEFAULT_CHAPTER_NAME);
        Chapter chapter = BookHelper.getChapter(chapterName);

        String sectionName = TagHelper.getStringValue(tags, "@section", null);
        String comment = classDoc.commentText();
        if (StringHelper.isEmpty(sectionName)) {
            //尝试使用注释第一行
            if (StringHelper.isNotEmpty(comment)) {
                sectionName = StringHelper.firstLine(comment);
                comment = StringHelper.deleteFirstLine(comment);
            }
        }
        String classFullName = classDoc.qualifiedTypeName(); //类全名
        if (StringHelper.isEmpty(sectionName)) {
            sectionName = classFullName;
        }

        Section section = BookHelper.getSections(chapter, sectionName);
        section.setCode(classFullName);

        String rankStr = TagHelper.getStringValue(tags, "@rank", "0");
        section.setRank(Integer.parseInt(rankStr));
        section.setComment(comment);

        MethodDoc[] methods = classDoc.methods();
        for (MethodDoc method : methods) {
            analyseApi(method, section, basePaths);
        }
    }

    private static void analyseApi(MethodDoc methodDoc, Section section, List<String> basePaths) {
        List<String> methods = null;
        AnnotationDesc.ElementValuePair[] vs = null;

        //@RequestMapping
        AnnotationDesc requestMappingAnnotation = AnnotationHelper.getAnnotation(methodDoc.annotations(), SpringMvcConstant.ANNOTATION_REQUEST_MAPPING);
        if (requestMappingAnnotation != null) {
            vs = requestMappingAnnotation.elementValues();
            methods = RequestMappingAnnotationHelper.getRequestMappingAnnotationValues(vs, "method");
        }

        //@GetMapping
        requestMappingAnnotation = AnnotationHelper.getAnnotation(methodDoc.annotations(), SpringMvcConstant.ANNOTATION_GET_MAPPING);
        if (requestMappingAnnotation != null) {
            vs = requestMappingAnnotation.elementValues();
            methods = Lists.newArrayList("GET");
        }

        //@PostMapping
        requestMappingAnnotation = AnnotationHelper.getAnnotation(methodDoc.annotations(), SpringMvcConstant.ANNOTATION_POST_MAPPING);
        if (requestMappingAnnotation != null) {
            vs = requestMappingAnnotation.elementValues();
            methods = Lists.newArrayList("POST");
        }

        List<String> paths = RequestMappingAnnotationHelper.getRequestMappingAnnotationValues(vs, "value");
        if (paths.isEmpty()) {
            paths.add("");
        }
        List<String> apiPaths = Lists.newArrayList();
        for (String basePath : basePaths) {
            for (String path : paths) {
                String apiPath;
                if (basePath.endsWith("/")) {
                    if (path.startsWith("/")) {
                        apiPath = basePath + path.substring(1);
                    } else {
                        apiPath = basePath + path;
                    }
                } else {
                    if (path.startsWith("/")) {
                        apiPath = basePath + path;
                    } else {
                        apiPath = basePath + "/" + path;
                    }
                }
                if (!apiPaths.contains(apiPath)) {
                    apiPaths.add(apiPath);
                }
            }
        }

        Api api = BaseApiInfoHelper.getApiBase(methodDoc, section);
        api.setPaths(apiPaths);
        api.setType(ApiTypeEnum.RESTFUL.getType());
        if (methods != null && !methods.isEmpty()) {
            api.setMethods(methods);
        }
        //如果是RestFul接口，且参数有@RequestBody时，强制为 POST
        if (hasRequestBodyParam(api)) {
            api.setMethods(Lists.newArrayList("POST"));
        }
    }

    private static boolean hasRequestBodyParam(Api api) {
        List<EntityRef> params = api.getRequestParams();
        if (params == null || params.isEmpty()) {
            return false;
        }
        for (EntityRef er : params) {
            if ("@RequestBody".equals(er.getAnnotation())) {
                return true;
            }
        }
        return false;
    }
}
