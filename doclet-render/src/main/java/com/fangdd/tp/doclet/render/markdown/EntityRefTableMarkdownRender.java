package com.fangdd.tp.doclet.render.markdown;

import com.fangdd.tp.doclet.render.EntityHandle;
import com.fangdd.tp.doclet.pojo.Entity;
import com.fangdd.tp.doclet.pojo.EntityRef;

import java.util.List;

/**
 * @author xuwenzhen
 * @date 18/1/18
 */
public class EntityRefTableMarkdownRender {
    public static String render(EntityRef entityRef) {
        StringBuilder sb = new StringBuilder();
        String entityName = entityRef.getEntityName();
        Entity entity = EntityHandle.getEntity(entityName);
        if (entityName.startsWith("java.") || (entity.getPrimitive() != null && entity.getPrimitive())) {
            if ((entity.getCollection() != null && entity.getCollection()) || (entity.getMap() != null && entity.getMap())) {
                List<EntityRef> parameteredEntityRefs = entity.getParameteredEntityRefs();
                for (EntityRef er : parameteredEntityRefs) {
                    render(er);
                }
            }
        } else {
            sb.append(render(entity));
        }
        return sb.toString();
    }

    public static String render(Entity entity) {
        StringBuilder sb = new StringBuilder();
        sb.append("类`");
        sb.append(entity.getName());
        sb.append("` 结构：");
        sb.append("\n\n");
        sb.append(EntityTableMarkdownRender.render(entity));
        sb.append("\n\n");
        return sb.toString();
    }
}
