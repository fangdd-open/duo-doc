package com.fangdd.tp.doclet.helper;

import com.fangdd.tp.doclet.analyser.EntityMateAnalyser;
import com.fangdd.tp.doclet.annotation.ParamAnnotation;
import com.fangdd.tp.doclet.enums.ApiPositionEnum;
import com.fangdd.tp.doclet.enums.ApiTypeEnum;
import com.fangdd.tp.doclet.pojo.Api;
import com.fangdd.tp.doclet.pojo.EntityRef;
import com.fangdd.tp.doclet.pojo.Section;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sun.javadoc.*;

import java.util.List;

/**
 * @author ycoe
 * @date 18/1/18
 */
public class BaseApiInfoHelper {
    private BaseApiInfoHelper() {
    }

    public static Api getApiBase(MethodDoc method, Section section) {
        Tag[] tags = method.tags();
        String apiName = TagHelper.getStringValue(tags, "@api", null);
        String since = TagHelper.getStringValue(tags, "@since", null);
        String author = TagHelper.getStringValue(tags, "@author", null);
        String deprecated = TagHelper.getStringValue(tags, "@deprecated", null);
        String returnComment = TagHelper.getStringValue(tags, "@return", null);
        Integer order = TagHelper.getIntegerValue(tags, "@c3");

        String comment = method.commentText();
        if (Strings.isNullOrEmpty(apiName)) {
            //如果接口名称为空，则尝试使用注释的第一行
            if (StringHelper.isNotEmpty(comment)) {
                apiName = StringHelper.firstLine(comment);
                comment = StringHelper.deleteFirstLine(comment);
            }
        }
        if (Strings.isNullOrEmpty(apiName)) {
            //如果接口名称还是为空，则使用方法名
            apiName = method.name();
        }


        //获取响应
        BookHelper.setApiMethod(method);
        BookHelper.setApiPosition(ApiPositionEnum.RESPONSE);
        EntityRef response = EntityMateAnalyser.analyse(method.returnType());
        response.setComment(returnComment);

        //参数
        ParamTag[] paramTags = method.paramTags();
        List<EntityRef> params = Lists.newArrayList();
        Parameter[] paramList = method.parameters();
        if (paramList != null && paramList.length > 0) {
            BookHelper.setApiPosition(ApiPositionEnum.PARAMETER);
            for (Parameter parameter : paramList) {
                EntityRef param = EntityMateAnalyser.analyse(parameter.type());
                if (paramTags != null) {
                    String paramComment = TagHelper.getStringValue(paramTags, parameter.name(), null);
                    param.setComment(paramComment);
                }
                param.setName(parameter.name());
                if (setParamAnnotations(param, parameter)) {
                    params.add(param);
                }
            }
        }
        String apiCode = section.getCode() + "." + method.name();

        String key = MD5Utils.md5(method.toString());
        Api api = BookHelper.getApi(section, key);
        api.setName(apiName);
        api.setType(ApiTypeEnum.DUBBO.getType());
        api.setComment(comment);
        api.setSince(since);
        api.setAuthor(author);
        api.setDeprecated(deprecated);
        api.setResponse(response);
        api.setRequestParams(params);
        api.setCode(apiCode);
        if (order != null) {
            api.setOrder(order);
        }
        return api;
    }

    private static boolean setParamAnnotations(EntityRef param, Parameter parameter) {
        AnnotationDesc[] paramAnnotations = parameter.annotations();
        if (paramAnnotations.length == 0) {
            //未指定时，默认为 @RequestParam
            ParamAnnotation paramAnnotation = DocHelper.getRestApiParamAnnotation(null);
            if (paramAnnotation != null) {
                return paramAnnotation.analyse(param, null);
            }
        }
        for (AnnotationDesc annotationDesc : paramAnnotations) {
            String annotation = annotationDesc.annotationType().toString();
            ParamAnnotation paramAnnotation = DocHelper.getRestApiParamAnnotation(annotation);
            if (paramAnnotation != null) {
                return paramAnnotation.analyse(param, annotationDesc);
            }
        }
        return true;
    }
}
