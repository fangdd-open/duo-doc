package com.fangdd.tp.doclet.render.markdown;

import com.fangdd.tp.doclet.pojo.Entity;
import com.fangdd.tp.doclet.pojo.EntityRef;
import com.fangdd.tp.doclet.render.EntityHandle;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @auth ycoe
 * @date 18/1/16
 */
public class EntityJsonMarkdownRender {
    private static final Map<String, String> SIMPLE_TYPE_DEFAULT_VALUES = Maps.newHashMap();

    static {
        SIMPLE_TYPE_DEFAULT_VALUES.put(String.class.getName(), "\"\"");
        SIMPLE_TYPE_DEFAULT_VALUES.put(Long.class.getName(), "0");
        SIMPLE_TYPE_DEFAULT_VALUES.put(Integer.class.getName(), "0");
        SIMPLE_TYPE_DEFAULT_VALUES.put(Double.class.getName(), "0.0");
        SIMPLE_TYPE_DEFAULT_VALUES.put(Float.class.getName(), "0.0");
        SIMPLE_TYPE_DEFAULT_VALUES.put(BigDecimal.class.getSimpleName(), "0.0");
    }

    private static Set<Entity> entryRenderPath = Sets.newHashSet();

    public static String render(EntityRef entityRef) {
        entryRenderPath.clear();
        Entity entity = EntityHandle.getEntity(entityRef.getEntityName());
        return render(entity, 0);
    }

    private static String render(Entity entity, int level) {
        if (entity.getCollection() != null && entity.getCollection()) {
            //如果是列表
            return renderCollection(entity, level);
        } else if (entity.getMap() != null && entity.getMap()) {
            //如果是Map
            return renderMap(entity, level);
        }

        //渲染普通类
        return renderEntity(entity, level);
    }

    private static String renderEntity(Entity entity, int level) {
        StringBuilder sb = new StringBuilder();
        if (entryRenderPath.contains(entity)) {
            sb.append(Strings.repeat("\t", level));
            sb.append("{\n");
            sb.append(Strings.repeat("\t", level + 1));
            sb.append("// 循环实体，详见上面 @");
            sb.append(entity.getName());
            sb.append("\n");
            sb.append(Strings.repeat("\t", level));
            sb.append("}");
            return sb.toString();

        }

        if (isPrimitive(entity)) {
            //是基础类型
            sb.append(Strings.repeat("\t", level));
            sb.append(renderDefaultBaseTypeDemo(entity.getName()));
            sb.append(" // @");
            sb.append(renderTypeName(entity.getName()));
            return sb.toString();
        }

        entryRenderPath.add(entity);

        sb.append(Strings.repeat("\t", level));
        sb.append("{ ");
        sb.append("// @");
        sb.append(renderTypeName(entity.getName()));
        String comment = entity.getComment();
        if (!Strings.isNullOrEmpty(comment)) {
            sb.append(" #");
            sb.append(renderComment(comment));
        }
        sb.append("\n");

        List<EntityRef> fields = entity.getFields();
        if (fields != null && !fields.isEmpty()) {
            //渲染每个属性
            for (int i = 0; i < fields.size(); i++) {
                EntityRef fieldRef = fields.get(i);
                sb.append(Strings.repeat("\t", level + 1));
                sb.append("\"");
                sb.append(fieldRef.getName());
                sb.append("\": ");
                sb.append(renderFieldDemo(fieldRef, level));
                if (i < fields.size() - 1) {
                    sb.append(",");
                }
                sb.append(" // ");
                sb.append(renderTypeName(fieldRef.getEntityName()));
                if (!Strings.isNullOrEmpty(fieldRef.getComment())) {
                    sb.append(" #");
                    sb.append(fieldRef.getComment());
                }
                if(fieldRef.isRequired()) {
                    sb.append(" @NotNull");
                }
                if (i < fields.size() - 1) {
                    sb.append("\n");
                }
            }
        }

        sb.append("\n");
        sb.append(Strings.repeat("\t", level));
        sb.append("}");
        return sb.toString();
    }

    private static String renderComment(String comment) {
        if (comment != null && comment.contains("\n")) {
            return comment.replaceAll("\\n", "\n//");
        }
        return comment;
    }

    private static String renderFieldDemo(EntityRef fieldRef, int level) {
        String entityName = fieldRef.getEntityName();
        Entity entity = EntityHandle.getEntity(entityName);
        String demoVal;
        if (entity.getCollection() != null && entity.getCollection()) {
            //如果是列表
            demoVal = renderCollection(entity, level + 1);
        } else if (entity.getMap() != null && entity.getMap()) {
            //如果是Map
            demoVal = renderMap(entity, level + 1);
        } else if (isPrimitive(entity)) {
            //原始数据类型
            demoVal = fieldRef.getDemo();
            if (demoVal == null) {
                return renderDefaultBaseTypeDemo(fieldRef.getEntityName());
            } else if (entityName.startsWith(String.class.getName())) {
                demoVal = "\"" + demoVal + "\"";
            } else if (entityName.startsWith(Date.class.getSimpleName())) {
                if(!demoVal.matches("^\\d+$")) {
                    //如果不是数值型
                    demoVal = "\"" + demoVal + "\"";
                }
            }
        } else {
            demoVal = renderEntity(entity, level + 1);
        }
        //清除后面的空格
        demoVal = demoVal.replaceAll("^\\s+", "");
        return demoVal;
    }

    private static boolean isPrimitive(Entity entity) {
        String entityName = entity.getName();
        return entityName.startsWith("java.lang") || (entity.getPrimitive() != null && entity.getPrimitive());
    }


    private static String renderDefaultBaseTypeDemo(String typeName) {
        String defaultValue = SIMPLE_TYPE_DEFAULT_VALUES.get(typeName);
        if (defaultValue == null) {
            defaultValue = "";
        }
        return defaultValue;
    }

    private static String renderMap(Entity entity, int level) {
        StringBuilder sb = new StringBuilder();
        sb.append(Strings.repeat("\t", level));
        sb.append("{ // @");
        sb.append(renderTypeName(entity.getName()));
        if (!Strings.isNullOrEmpty(entity.getComment())) {
            sb.append(" #");
            sb.append(entity.getComment());
        }
        sb.append("\n");

        List<EntityRef> ps = entity.getParameteredEntityRefs();
        if (ps.size() == 2) {
            EntityRef keyRef = ps.get(0);
            EntityRef valueRef = ps.get(1);
            sb.append(Strings.repeat("\t", level + 1));
            sb.append(renderFieldDemo(keyRef, level + 1));
            sb.append(": ");
            sb.append(renderFieldDemo(valueRef, level + 1));
        }
        sb.append("\n");
        sb.append(Strings.repeat("\t", level));
        sb.append("}");
        return sb.toString();
    }

    private static String renderCollection(Entity entity, int level) {
        StringBuilder sb = new StringBuilder();
        sb.append(Strings.repeat("\t", level));
        sb.append("[\n");
        List<EntityRef> parameteredEntityRefs = entity.getParameteredEntityRefs();
        if (parameteredEntityRefs != null && !parameteredEntityRefs.isEmpty()) {
            EntityRef parameterEntityRef = parameteredEntityRefs.get(0);
            if (parameterEntityRef != null) {
                Entity parameteredEntity = EntityHandle.getEntity(parameterEntityRef.getEntityName());
                sb.append(render(parameteredEntity, level + 1));
            }
        }
        sb.append("\n");
        sb.append(Strings.repeat("\t", level));
        sb.append("]");
        return sb.toString();
    }

    private static String renderTypeName(String typeName) {
        return typeName.replaceAll("java.util.", "")
                .replaceAll("java.lang.", "");
    }
}
