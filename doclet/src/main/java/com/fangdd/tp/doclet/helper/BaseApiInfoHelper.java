package com.fangdd.tp.doclet.helper;

import com.fangdd.tp.doclet.analyser.EntityMateAnalyser;
import com.fangdd.tp.doclet.constant.SpringMvcConstant;
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
 * @auth ycoe
 * @date 18/1/18
 */
public class BaseApiInfoHelper {
    public static Api getApiBase(MethodDoc method, Section section) {
        Tag[] tags = method.tags();
        String apiName = TagHelper.getStringValue(tags, "@chapter", null);
        String since = TagHelper.getStringValue(tags, "@since", null);
        String author = TagHelper.getStringValue(tags, "@author", null);
        String deprecated = TagHelper.getStringValue(tags, "@deprecated", null);
        String rank = TagHelper.getStringValue(tags, "@rank", "0");
        String returnComment = TagHelper.getStringValue(tags, "@return", null);

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
                if(!setParamAnnotations(param, parameter)) {
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
        api.setRank(Integer.parseInt(rank));
        api.setResponse(response);
        api.setRequestParams(params);
        api.setCode(apiCode);
        return api;
    }

    private static boolean setParamAnnotations(EntityRef param, Parameter parameter) {
        AnnotationDesc[] paramAnnotations = parameter.annotations();
        for (AnnotationDesc annotationDesc : paramAnnotations) {
            String annotation = annotationDesc.annotationType().toString();
            if (SpringMvcConstant.ANNOTATION_PATH_VARIABLE.equals(annotation)) {
                // @PathVariable
                param.setRequired(true);
                param.setAnnotation("@PathVariable");
            } else if (SpringMvcConstant.ANNOTATION_REQUEST_BODY.equals(annotation)) {
                // @RequestBody
                param.setRequired(true);
                param.setAnnotation("@RequestBody");
            } else if (SpringMvcConstant.ANNOTATION_REQUEST_ATTRIBUTE.equals(annotation)) {
                // @RequestAttribute， 由此注解的属性一般是由统一拦截器或Filter中设置进去的，所以不添加进参数列表中
                param.setRequired(true);
                param.setAnnotation("@RequestAttribute");
                return false;
            } else if (SpringMvcConstant.ANNOTATION_REQUEST_PARAM.equals(annotation)) {
                // @RequestParam
                AnnotationValue requiredVal = AnnotationHelper.getValue(annotationDesc, "required");
                Boolean required = true;
                if (requiredVal != null) {
                    required = (Boolean) requiredVal.value();
                }
                String defalutValue = AnnotationHelper.getStringValue(annotationDesc, "defaultValue");
                param.setRequired(required);
                param.setDemo(defalutValue);
                param.setAnnotation("@RequestParam");
            }
        }

        return true;
    }
}
