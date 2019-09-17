package com.fangdd.tp.doclet.analyser;

import com.fangdd.tp.doclet.DocletConfig;
import com.fangdd.tp.doclet.analyser.entity.*;
import com.fangdd.tp.doclet.constant.EntityConstant;
import com.fangdd.tp.doclet.helper.BookHelper;
import com.fangdd.tp.doclet.helper.Logger;
import com.fangdd.tp.doclet.helper.TagHelper;
import com.fangdd.tp.doclet.pojo.Entity;
import com.fangdd.tp.doclet.pojo.EntityRef;
import com.fangdd.tp.doclet.render.EntityHandle;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sun.javadoc.*;
import com.sun.tools.javadoc.ClassDocImpl;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author xuwenzhen
 * @date 18/1/9
 */
public class EntityMateAnalyser {
    private static final String COLLECTION_CLASS_NAME = Collection.class.getName();
    private static final String MAP_CLASS_NAME = Map.class.getName();
    private static final String BOOLEAN = "boolean";
    private static final Logger logger = new Logger();
    private static final Map<String, EntityFieldAnnotationAnalyser> FIELD_ANNOTATION_ANALYSER_MAP = Maps.newHashMap();
    private static final String STR_LT = "<";
    private static final String STR_GT = ">";
    private static final String STR_SQUARE_LEFT = "[";
    private static final String STR_SQUARE_RIGHT = "]";
    private static final String STR_SQUARES = "[]";
    private static final String JAVA_PACKAGE = "java.";
    private static final String SERIAL_VERSION_UID = "serialVersionUID";
    private static final String LOMBOK_DATA = "@lombok.Data";

    static {
        FIELD_ANNOTATION_ANALYSER_MAP.put(EntityConstant.ANNOTATION_DATE_TIME_FORMAT, new DateTimeFormatFieldAnnotationAnalyser());
        FIELD_ANNOTATION_ANALYSER_MAP.put(EntityConstant.JAVAX_VALIDATION_CONSTRAINTS_NOTNULL, new NotNullFieldAnnotationAnalyser());
        FIELD_ANNOTATION_ANALYSER_MAP.put(EntityConstant.JAVAX_VALIDATION_CONSTRAINTS_NULL, new NotNullFieldAnnotationAnalyser());
        FIELD_ANNOTATION_ANALYSER_MAP.put(EntityConstant.HIBERNATE_VALIDATOR_ANNOTATION_NOT_BLANK, new NotNullFieldAnnotationAnalyser());
        FIELD_ANNOTATION_ANALYSER_MAP.put(EntityConstant.HIBERNATE_VALIDATOR_ANNOTATION_NOT_EMPTY, new NotNullFieldAnnotationAnalyser());
        FIELD_ANNOTATION_ANALYSER_MAP.put(EntityConstant.GRAPHQL_FIELD, new GraphqlFieldAnnotationAnalyser());
        FIELD_ANNOTATION_ANALYSER_MAP.put(EntityConstant.ANNOTATION_GRAPHQL_DIRECTIVE, new GraphqlDirectiveAnnotationAnalyser());
    }

    private static final Set<String> CURRENT_CLASS_FIELD_NAMES = Sets.newHashSet();

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

        List<EntityRef> parameterizedEntityRefList = Lists.newArrayList();
        Map<String, Type> parameterizedTypeMap = getParameterizedTypeMap(type, parentParameterizedTypeMap, parameterizedEntityRefList);

        String fullName = getFullName(type, parameterizedTypeMap);

        Entity entity = EntityHandle.getEntity(fullName);
        if (entity != null) {
            return entity;
        }
        entity = new Entity();
        entity.setName(fullName);
        entity.setPrimitive(false);
        entity.setEnumerate(classDoc.isEnum());

        Tag[] tags = classDoc.tags();
        String since = TagHelper.getStringValue(tags, DocletConfig.tagSince, null);
        String deprecated = TagHelper.getStringValue(tags, DocletConfig.tagDeprecated, null);
        String comment = classDoc.commentText();

        entity.setSince(since);
        entity.setDeprecated(deprecated);
        entity.setComment(comment);
        entity.setDefaultValue("");
        if (type.dimension().startsWith(STR_SQUARES)) {
            //数组
            entity.setArray(true);
        }
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

        if (fullName.startsWith(JAVA_PACKAGE)) {
            return entity;
        }

        if (classDoc.isEnum()) {
            //如果是枚举，处理枚举元素
            analyseEnumItems(classDoc, fieldItems);
        } else {
            //处理实体类属性
            analyseEntityFields(classDoc, parameterizedTypeMap, fieldItems);
        }

        return entity;
    }

    private static Map<String, Type> getParameterizedTypeMap(Type type, Map<String, Type> parentParameterizedTypeMap, List<EntityRef> parameterizedEntityRefList) {
        ClassDoc classDoc = type.asClassDoc();
        String className = classDoc.toString();
        Map<String, Type> parameterizedTypeMap = Maps.newHashMap();
        boolean isParameterizedType = className.contains(STR_LT) || className.contains(STR_GT) && parentParameterizedTypeMap != null;
        if (isParameterizedType) {
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
                            String typeArgumentName = typeArgument.toString();
                            if (typeArgumentName.contains(" extends "))  {
                                typeArgumentName =  typeArgumentName.substring(0, typeArgumentName.indexOf(' '));
                            }
                            parameterType = parentParameterizedTypeMap.get(typeArgumentName);
                            parameterizedTypeMap.put(typeArgumentName, parameterType);
                        } else {
                            parameterizedTypeMap.put(typeParameterName, typeArgument);
                        }
                        if (parameterType != null) {
                            parameterizedTypeList.add(parameterType);
                        } else {
                            logger.info("接口"
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
        return parameterizedTypeMap;
    }

    private static void analyseEnumItems(ClassDoc classDoc, List<EntityRef> fieldItems) {
        FieldDoc[] enumConstants = classDoc.enumConstants();
        if (enumConstants != null) {
            for (FieldDoc item : enumConstants) {
                EntityRef fieldRef = new EntityRef();
                fieldRef.setEntityName(item.name());
                fieldRef.setComment(item.commentText());
                fieldRef.setName(item.name());
                fieldItems.add(fieldRef);
            }
        }
    }

    private static void analyseEntityFields(ClassDoc classDoc, Map<String, Type> parameterizedTypeMap, List<EntityRef> fieldItems) {
        CURRENT_CLASS_FIELD_NAMES.clear();
        List<FieldDoc> fields = getFields(classDoc);
        if (fields != null) {
            boolean lombok = isLombok(classDoc);
            Set<String> setterFields = getGetterFields(classDoc);

            for (FieldDoc field : fields) {
                //解析属性类型
                if (!lombok && !availableField(setterFields, field)) {
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
                String required = TagHelper.getStringValue(fieldTags, DocletConfig.tagRequired, null);
                String disable = TagHelper.getStringValue(fieldTags, DocletConfig.tagDisable, null);
                if (disable != null) {
                    continue;
                }
                String demo = TagHelper.getStringValue(fieldTags, DocletConfig.tagDemo, null);
                if (Strings.isNullOrEmpty(demo) && fieldEntity.getEnumerate() != null && fieldEntity.getEnumerate()) {
                    //如果是枚举，且@demo为空
                    FieldDoc[] enumConstants = ((ClassDocImpl) fieldType).enumConstants();
                    if (enumConstants != null && enumConstants.length > 0) {
                        demo = enumConstants[0].name();
                    }
                }

                EntityRef fieldRef = new EntityRef();
                fieldRef.setEntityName(fieldEntity.getName());
                fieldRef.setComment(fieldComment);
                fieldRef.setName(field.name());
                fieldRef.setRequired(required != null);
                fieldRef.setDemo(demo);

                //设置各属性的注解
                if (!readFieldAnnotation(fieldRef, field)) {
                    continue;
                }

                fieldItems.add(fieldRef);
            }
        }
    }

    private static boolean isLombok(ClassDoc classDoc) {
        AnnotationDesc[] annotations = classDoc.annotations();
        if (annotations.length > 0) {
            for (int i = 0; i < annotations.length; i++) {
                AnnotationDesc annotation = annotations[0];
                if (LOMBOK_DATA.equals(annotation.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean readFieldAnnotation(EntityRef fieldRef, FieldDoc field) {
        AnnotationDesc[] annotations = field.annotations();
        if (annotations == null || annotations.length == 0) {
            return true;
        }

        for (AnnotationDesc annotationDesc : annotations) {
            EntityFieldAnnotationAnalyser analyser = FIELD_ANNOTATION_ANALYSER_MAP.get(annotationDesc.annotationType().qualifiedName());
            if (analyser != null && analyser.check(fieldRef, field)) {
                if (!analyser.analyse(annotationDesc, fieldRef, field)) {
                    return false;
                }
            }
        }
        return true;
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
            if (SERIAL_VERSION_UID.equals(field.name())) {
                continue;
            }
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
        boolean isCollection = (typeName.contains(STR_SQUARE_LEFT) && typeName.contains(STR_SQUARE_RIGHT)) || typeName.startsWith(COLLECTION_CLASS_NAME);
        if (isCollection) {
            return true;
        }
        ClassDoc classDoc = type.asClassDoc();
        ClassDoc[] interfaces = classDoc.interfaces();
        if (interfaces != null) {
            for (ClassDoc interfaceClassDoc : interfaces) {
                if (COLLECTION_CLASS_NAME.equals(interfaceClassDoc.qualifiedName())) {
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

        if (!typeName.contains(STR_LT)) {
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
