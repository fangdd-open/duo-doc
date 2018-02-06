package com.fangdd.tp.doclet.render;

import com.fangdd.tp.doclet.pojo.Entity;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @auth ycoe
 * @date 18/1/21
 */
public class EntityHandle {
    private static Map<String, Entity> entityMap = Maps.newHashMap();

    public static Map<String, Entity> getEntityMap() {
        return entityMap;
    }

    public static Entity getEntity(String entityName) {
        return entityMap.get(entityName);
    }

    public static void addEntity(Entity entity) {
        entityMap.put(entity.getName(), entity);
    }
}
