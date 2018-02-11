package com.fangdd.tp.doclet.render.markdown;

import com.fangdd.tp.doclet.pojo.Api;
import com.fangdd.tp.doclet.pojo.EntityRef;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Set;

/**
 * @auth ycoe
 * @date 18/1/15
 */
public class RestApiMarkdownRender {
    public static String render(Api api) {
        StringBuilder sb = new StringBuilder();
        sb.append("#### 接口");

        List<String> paths = api.getPaths();
        if (paths.size() == 1) {
            sb.append("：");
        } else {
            sb.append("\n\n");
        }
        for (String path : paths) {
            sb.append("`");
            sb.append(path);
            sb.append("`\n");
        }
        sb.append("\n");


        String comment = api.getComment();
        if (!Strings.isNullOrEmpty(comment)) {
            sb.append("> ");
            sb.append(comment);
            sb.append("\n\n");
        }

        sb.append("#### 方法： ");
        List<String> methods = api.getMethods();
        if (methods == null || methods.isEmpty()) {
            methods = Lists.newArrayList("GET", "POST");
        }
        for (int i = 0; i < methods.size(); i++) {
            if (i > 0) {
                sb.append(" / ");
            }
            sb.append("`");
            sb.append(methods.get(i));
            sb.append("`");
        }
        sb.append("\n\n");

        List<EntityRef> params = api.getRequestParams();
        int paramLen = params.size();

        if (paramLen > 0) {
            sb.append("#### 参数\n\n");
            sb.append("| 参数 | 类型 | 来源 | 是否必填 | 备注 |\n");
            sb.append("| :-- | :-- | :-- | :-- | :-- |\n");

            for (int i = 0; i < paramLen; i++) {
                EntityRef er = params.get(i);
                sb.append("| ");
                sb.append(er.getName());
                sb.append(" | ");
                sb.append(er.getEntityName());
                sb.append(" | ");
                sb.append(er.getAnnotation());
                sb.append(" | ");
                sb.append(er.isRequired() ? "Y" : "N");
                sb.append(" | ");
                sb.append(er.getComment());
                sb.append(" |\n");
            }
            sb.append("\n");
        }

        if (params != null) {
            for (int i = 0; i < paramLen; i++) {
                EntityRef entityRef = params.get(i);
                sb.append(EntityRefTableMarkdownRender.render(entityRef));
            }
        }

        String serviceName = getServiceName(api.getCode());
        String refName = getRefName(serviceName);

        sb.append("#### 响应\n\n");
        EntityRef response = api.getResponse();
        String responseComment = response.getComment();
        if (!Strings.isNullOrEmpty(responseComment)) {
            sb.append("> ");
            sb.append(responseComment);
            sb.append("\n\n");
        }

        sb.append("响应体： `");
        sb.append(response.getEntityName());
        sb.append("`\n\n");
        sb.append("``` javascript\n");
        sb.append(EntityJsonMarkdownRender.render(response));
        sb.append("\n```\n\n");

        return sb.toString();
    }


    private static String getServiceName(String code) {
        int index = code.lastIndexOf('.');
        return code.substring(0, index);
    }

    private static String getRefName(String serviceName) {
        int index = serviceName.lastIndexOf('.');
        String refName = serviceName.substring(index + 1);
        return refName.substring(0, 1).toLowerCase() + refName.substring(1);
    }
}
