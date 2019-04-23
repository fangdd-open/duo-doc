package com.fangdd.tp.doclet.render.markdown;

import com.fangdd.tp.doclet.pojo.Api;
import com.fangdd.tp.doclet.render.EntityHandle;
import com.fangdd.tp.doclet.pojo.Entity;
import com.fangdd.tp.doclet.pojo.EntityRef;

import java.util.List;

/**
 * @author ycoe
 * @date 18/1/15
 */
public class DubboApiMarkdownRender {
    public static String render(Api api) {
        StringBuilder sb = new StringBuilder();
        sb.append("##### 接口\n\n``` java\n");
        List<EntityRef> params = api.getRequestParams();
        int paramLen = params == null ? 0 : params.size();
        sb.append(getDubboApi(api));
        sb.append("```\n\n");

        if (params != null) {
            for (int i = 0; i < paramLen; i++) {
                EntityRef entityRef = params.get(i);
                sb.append(renderEntityTable(entityRef));
            }
        }

        sb.append("##### Maven引用\n\n");
        sb.append("``` xml\n");
        sb.append("<dependency>\n");
        sb.append("\t<groupId>");
        sb.append(api.getArtifact().getGroupId());
        sb.append("</groupId>\n");
        sb.append("\t<artifactId>");
        sb.append(api.getArtifact().getArtifactId());
        sb.append("</artifactId>\n");
        sb.append("\t<version>");
        sb.append(api.getArtifact().getVersion());
        sb.append("</version>\n");
        sb.append("</dependency>\n");
        sb.append("```\n\n");

        sb.append("##### Gradle引用\n\n");
        sb.append("``` json\n");
        sb.append("compile '");
        sb.append(api.getArtifact().getGroupId());
        sb.append(":");
        sb.append(api.getArtifact().getArtifactId());
        sb.append(":");
        sb.append(api.getArtifact().getVersion());
        sb.append("'\n");
        sb.append("```\n\n");

        String serviceName = getServiceName(api.getCode());
        String refName = getRefName(serviceName);
        sb.append("#### Spring配置\n\n");
        sb.append("``` xml\n");
        sb.append("<dubbo:service interface=\"");
        sb.append(serviceName);
        sb.append("\" ref=\"");
        sb.append(refName);
        sb.append("\" protocol=\"dubbo\" version=\"");
        sb.append(api.getVersion());
        sb.append("\"/>\n");
        sb.append("```\n\n");

        sb.append("#### 响应\n\n响应体： `");
        sb.append(api.getResponse().getEntityName());
        sb.append("`\n\n");
        sb.append("``` javascript\n");
        sb.append(EntityJsonMarkdownRender.render(api.getResponse()));
        sb.append("\n```\n\n");

        return sb.toString();
    }

    public static String getDubboApi(Api api) {
        StringBuilder sb = new StringBuilder();
        List<EntityRef> params = api.getRequestParams();
        int paramLen = params == null ? 0 : params.size();
        sb.append("/**");
        String comment = api.getComment();
        if (comment != null && comment.length() > 0) {
            sb.append("\n * ");
            sb.append(comment);
        }
        sb.append("\n * ");
        if (params != null) {
            for (int i = 0; i < paramLen; i++) {
                EntityRef paramRef = params.get(i);
                sb.append("\n * @param ");
                sb.append(paramRef.getName());
                sb.append(" ");
                sb.append(paramRef.getComment());
            }
        }
        sb.append("\n * @return ");
        sb.append(api.getResponse().getComment());
        sb.append("\n */\n");
        EntityRef resp = api.getResponse();
        sb.append(resp.getEntityName());
        sb.append(" ");
        sb.append(api.getCode());
        sb.append("(");
        if (params != null) {
            for (int i = 0; i < paramLen; i++) {
                EntityRef paramRef = params.get(i);
                sb.append(paramRef.getEntityName());
                sb.append(" ");
                sb.append(paramRef.getName());
                if (i < paramLen - 1) {
                    sb.append(", ");
                }
            }
        }
        sb.append(");\n");
        return sb.toString();
    }

    private static String renderEntityTable(EntityRef entityRef) {
        StringBuilder sb = new StringBuilder();
        String entityName = entityRef.getEntityName();
        Entity entity = EntityHandle.getEntity(entityName);
        if (entityName.startsWith("java.")) {
            if ((entity.getCollection() != null && entity.getCollection()) || (entity.getMap() != null && entity.getMap())) {
                List<EntityRef> parameteredEntityRefs = entity.getParameteredEntityRefs();
                for (EntityRef er : parameteredEntityRefs) {
                    renderEntityTable(er);
                }
            }
        } else {
            sb.append(EntityRefTableMarkdownRender.render(entity));
        }
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
