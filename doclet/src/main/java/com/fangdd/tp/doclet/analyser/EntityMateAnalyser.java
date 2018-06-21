package com.fangdd.tp.doclet.analyser;

import com.fangdd.tp.doclet.analyser.entity.DateTimeFormatFieldAnnotationAnalyser;
import com.fangdd.tp.doclet.analyser.entity.EntityFieldAnnotationAnalyser;
import com.fangdd.tp.doclet.analyser.entity.NotNullFieldAnnotationAnalyser;
import com.fangdd.tp.doclet.constant.EntityConstant;
import com.fangdd.tp.doclet.helper.BookHelper;
import com.fangdd.tp.doclet.helper.TagHelper;
import com.fangdd.tp.doclet.pojo.Entity;
import com.fangdd.tp.doclet.pojo.EntityRef;
import com.fangdd.tp.doclet.render.EntityHandle;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sun.javadoc.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author ycoe
 * @date 18/1/9
 */
public class EntityMateAnalyser {
    public static final String Collection_CLASS_NAME = Collection.class.getName();
    public static final String MAP_CLASS_NAME = Map.class.getName();
    public static final String BOOLEAN = "boolean";
    public static final Map<String, EntityFieldAnnotationAnalyser> FIELD_ANNOTATION_ANALYSER_MAP = Maps.newHashMap();

    static {
        FIELD_ANNOTATION_ANALYSER_MAP.put(EntityConstant.ANNOTATION_DATE_TIME_FORMAT, new DateTimeFormatFieldAnnotationAnalyser());
        FIELD_ANNOTATION_ANALYSER_MAP.put(EntityConstant.JAVAX_VALIDATION_CONSTRAINTS_NOTNULL, new NotNullFieldAnnotationAnalyser());
        FIELD_ANNOTATION_ANALYSER_MAP.put(EntityConstant.JAVAX_VALIDATION_CONSTRAINTS_NULL, new NotNullFieldAnnotationAnalyser());
        FIELD_ANNOTATION_ANALYSER_MAP.put(EntityConstant.HIBERNATE_VALIDATOR_ANNOTATION_NOT_BLANK, new NotNullFieldAnnotationAnalyser());
        FIELD_ANNOTATION_ANALYSER_MAP.put(EntityConstant.HIBERNATE_VALIDATOR_ANNOTATION_NOT_EMPTY, new NotNullFieldAnnotationAnalyser());
    }

    private static Set<String> CURRENT_CLASS_FIELD_NAMES = Sets.newHashSet();

    public static EntityRef analyse(Type type) {
        return getEntityRef(type, null);
    }

    private static EntityRef getEntityRef(Type type, Map<String, Type> parameterizedTypeMap) {
        Entity entity = getEntity(type, parameterizedTypeMap);
        EntityRef entityRef = new EntityRef();
        entityRef.setEntityName(entity.getName());

        return entityRef;
    }

    private static Entity getEntity(Type type, Map<String, Type> parentParameterizedTypeMap) {
        if (isPrimitive(type)) {
            //如果是原始数据类型
            return getPrimitiveTypeEntity(type);
        }
        ClassDoc classDoc = type.asClassDoc();
        String className = classDoc.toString();

        List<EntityRef> parameterizedEntityRefList = Lists.newArrayList();
        Map<String, Type> parameterizedTypeMap = Maps.newHashMap();
        if (className.contains("<") || className.contains(">")) {
            //有泛型
            ParameterizedType parameterizedType = type.asParameterizedType();
            if (parameterizedType != null) {
                TypeVariable[] typeParameters = classDoc.typeParameters();
                Type[] typeArguments = parameterizedType.typeArguments();
                if (typeArguments != null && typeArguments.length > 0) {
                    List<Type> parameterizedTypeList = Lists.newArrayList();
                    for (int i = 0; i < typeArguments.length; i++) {
                        Type typeArgument = typeArguments[i];
                        String typeParameterName = typeParameters[i].typeName();
                        Type parameterType = typeArgument;
                        if (typeArgument.asTypeVariable() != null) {
                            parameterType = parentParameterizedTypeMap.get(typeArgument.toString());
                            parameterizedTypeMap.put(typeArgument.toString(), parameterType);
                        } else {
                            parameterizedTypeMap.put(typeParameterName, typeArgument);
                        }
                        if (parameterType != null) {
                            parameterizedTypeList.add(parameterType);
                        } else {
                            System.out.println("接口"
                                    + BookHelper.getCurrentMethod()
                                    + " " + BookHelper.getApiPosition().getTitle()
                                    + " " + className + "<===未指定泛型类型");
                            parameterizedTypeList.add(null);
                        }
                    }

                    for (Type item : parameterizedTypeList) {
                        if (item == null) {
                            parameterizedEntityRefList.add(null);
                            continue;
                        }
                        EntityRef parameterEntityRef = getEntityRef(item, null);
                        parameterizedEntityRefList.add(parameterEntityRef);
                    }
                }
            }
        }


        String fullName = getFullName(type, parameterizedTypeMap);

        Entity entity = EntityHandle.getEntity(fullName);
        if (entity != null) {
            return entity;
        }
        entity = new Entity();
        entity.setName(fullName);
        entity.setPrimitive(false);

        Tag[] tags = classDoc.tags();
        String since = TagHelper.getStringValue(tags, "@since", null);
        String deprecated = TagHelper.getStringValue(tags, "@deprecated", null);
        String comment = classDoc.commentText();

        entity.setSince(since);
        entity.setDeprecated(deprecated);
        entity.setComment(comment);
        entity.setDefaultValue("");
        if (!parameterizedEntityRefList.isEmpty()) {
            entity.setParameteredEntityRefs(parameterizedEntityRefList);
            //设置是否Map or List
            if (isCollection(type)) {
                entity.setCollection(true);
            } else if (isMap(type)) {
                entity.setMap(true);
            }
        }

        List<EntityRef> fieldItems = Lists.newArrayList();
        entity.setFields(fieldItems);

        EntityHandle.addEntity(entity);

        if (fullName.startsWith("java.")) {
            return entity;
        }

        //处理属性
        CURRENT_CLASS_FIELD_NAMES.clear();
        List<FieldDoc> fields = getFields(classDoc);
        if (fields != null) {
            Set<String> setterFields = getGetterFields(classDoc);

            for (FieldDoc field : fields) {
                //解析属性类型
                if (!availableField(setterFields, field)) {
                    continue;
                }
                String fieldComment = field.commentText();
                Type fieldType = field.type();
                String fieldTypeName = fieldType.typeName();
                if (parameterizedTypeMap.containsKey(fieldTypeName)) {
                    fieldType = parameterizedTypeMap.get(fieldTypeName);
                }
                Entity fieldEntity = getEntity(fieldType, parameterizedTypeMap);

                Tag[] fieldTags = field.tags();
                String required = TagHelper.getStringValue(fieldTags, "@required", null);
                String demo = TagHelper.getStringValue(fieldTags, "@demo", null);

                EntityRef fieldRef = new EntityRef();
                fieldRef.setEntityName(fieldEntity.getName());
                fieldRef.setComment(fieldComment);
                fieldRef.setName(field.name());
                fieldRef.setRequired(required != null);
                fieldRef.setDemo(demo);

                //设置各属性的注解
                readFieldAnnotation(fieldRef, field);

                fieldItems.add(fieldRef);
            }
        }

        return entity;
    }

    private static void readFieldAnnotation(EntityRef fieldRef, FieldDoc field) {
        AnnotationDesc[] annotations = field.annotations();
        if (annotations == null || annotations.length == 0) {
            return;
        }

        for (AnnotationDesc annotationDesc : annotations) {
            EntityFieldAnnotationAnalyser analyser = FIELD_ANNOTATION_ANALYSER_MAP.get(annotationDesc.annotationType().qualifiedName());
            if (analyser != null) {
                analyser.analyse(annotationDesc, fieldRef, field);
            }
        }
    }

    private static boolean isPrimitive(Type type) {
        if (type.isPrimitive()) {
            return true;
        }
        String typeName = type.qualifiedTypeName();
        if (typeName.equals(Date.class.getName())) {
            return true;
        }
        if (typeName.equals(BigDecimal.class.getName())) {
            return true;
        }

        return false;
    }

    private static List<FieldDoc> getFields(ClassDoc classDoc) {
        List<FieldDoc> fields = Lists.newArrayList();
        FieldDoc[] fieldArray = classDoc.fields(false);
        for (int i = 0; i < fieldArray.length; i++) {
            FieldDoc field = fieldArray[i];
            if (CURRENT_CLASS_FIELD_NAMES.contains(field.name())) {
                continue;
            }
            CURRENT_CLASS_FIELD_NAMES.add(field.name());
            fields.add(field);
        }

        ClassDoc superClass = classDoc.superclass();
        if (superClass != null && !superClass.qualifiedName().equals(Object.class.getName())) {
            List<FieldDoc> superClassFields = getFields(superClass);
            if (!superClassFields.isEmpty()) {
                fields.addAll(superClassFields);
            }
        }

        return fields;
    }

    private static Entity getPrimitiveTypeEntity(Type type) {
        String fullName = type.typeName();
        Entity entity = EntityHandle.getEntity(fullName);
        if (entity != null) {
            return entity;
        }
        entity = new Entity();
        entity.setName(fullName);
        entity.setPrimitive(true);

        entity.setDefaultValue("");
        List<EntityRef> fieldItems = Lists.newArrayList();
        entity.setFields(fieldItems);

        EntityHandle.addEntity(entity);

        return entity;
    }

    private static Set<String> getGetterFields(ClassDoc classDoc) {
        Set<String> pojoPropertySet = Sets.newHashSet();
        MethodDoc[] methods = classDoc.methods();
        for (MethodDoc method : methods) {
            String name = method.name();
            if (name.startsWith("get")) {
                String fieldName = name.substring(3);
                Type returnType = method.returnType();
                if (BOOLEAN.equalsIgnoreCase(returnType.typeName())) {
                    pojoPropertySet.add("is" + fieldName);
                }
                if (fieldName.length() > 1) {
                    fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
                    pojoPropertySet.add(fieldName);
                }
            }
        }

        ClassDoc superClass = classDoc.superclass();
        if (superClass != null && !superClass.qualifiedName().equals(Object.class.getName())) {
            Set<String> superClassFields = getGetterFields(superClass);
            if (!superClassFields.isEmpty()) {
                pojoPropertySet.addAll(superClassFields);
            }
        }

        return pojoPropertySet;
    }

    /**
     * 判断此属性是否有效属性（有setter & getter）
     *
     * @param setterFields
     * @param field
     * @return
     */
    private static boolean availableField(Set<String> setterFields, FieldDoc field) {
        if (field.isStatic()) {
            return false;
        }
        String fieldName = field.name();
        return setterFields.contains(fieldName);
    }

    private static boolean isMap(Type type) {
        ClassDoc classDoc = type.asClassDoc();
        if (MAP_CLASS_NAME.equals(classDoc.qualifiedName())) {
            return true;
        }
        ClassDoc[] interfaces = classDoc.interfaces();
        if (interfaces != null) {
            for (ClassDoc interfaceClassDoc : interfaces) {
                if (MAP_CLASS_NAME.equals(interfaceClassDoc.qualifiedName())) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isCollection(Type type) {
        String typeName = type.toString();
        if ((typeName.contains("[") && typeName.contains("]")) || typeName.startsWith(Collection_CLASS_NAME)) {
            return true;
        }
        ClassDoc classDoc = type.asClassDoc();
        ClassDoc[] interfaces = classDoc.interfaces();
        if (interfaces != null) {
            for (ClassDoc interfaceClassDoc : interfaces) {
                if (Collection_CLASS_NAME.equals(interfaceClassDoc.qualifiedName())) {
                    return true;
                }
            }
        }

        return false;
    }

    private static String getFullName(Type type, Map<String, Type> parentParameterizedTypeMap) {
        String typeName = type.toString();
        if (parentParameterizedTypeMap == null || parentParameterizedTypeMap.isEmpty()) {
            return typeName;
        }

        if (!typeName.contains("<")) {
            return typeName;
        }

        for (Map.Entry<String, Type> entry : parentParameterizedTypeMap.entrySet()) {
            Type parentParameterizedType = entry.getValue();
            if (parentParameterizedType != null) {
                typeName = typeName.replaceAll("(" + entry.getKey() + ")(,|>)", parentParameterizedType.toString() + "$2");
            }
        }

        return typeName;
    }
}
