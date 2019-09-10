package com.fangdd.tp.doclet.helper;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationValue;

import java.util.Iterator;
import java.util.List;

/**
 * @author xuwenzhen
 * @date 16/1/27
 */
public class AnnotationHelper {
    private AnnotationHelper() {
    }

    public static AnnotationDesc getAnnotation(AnnotationDesc[] annotations, String annotationName) {
        if (annotations == null || annotations.length == 0) {
            return null;
        }

        for (AnnotationDesc annotationDesc : annotations) {
            if (annotationDesc.annotationType().qualifiedName().equals(annotationName)) {
                return annotationDesc;
            }
        }
        return null;
    }

    public static AnnotationValue getValue(AnnotationDesc requestMappingAnnotation, String name) {
        if (requestMappingAnnotation == null) {
            return null;
        }
        AnnotationDesc.ElementValuePair[] evs = requestMappingAnnotation.elementValues();
        for (AnnotationDesc.ElementValuePair ev : evs) {
            if (ev.element().name().equals(name)) {
                return ev.value();
            }
        }
        return null;
    }

    public static String getStringValue(AnnotationDesc annotationDesc, String name) {
        AnnotationValue annotationValue = getValue(annotationDesc, name);
        if (annotationValue == null) {
            return null;
        }
        return StringHelper.cleanQuotation(annotationValue.toString());
    }

    public static String getStringValues(AnnotationDesc annotationDesc, String name, String joiner) {
        AnnotationValue annotationValue = getValue(annotationDesc, name);
        if (annotationValue == null) {
            return null;
        }
        Object values = annotationValue.value();
        List vs;
        if (Iterator.class.isInstance(values)) {
            vs = cleanQuotation((Iterable<?>) values);
        } else if (values.getClass().isArray()) {
            vs = cleanQuotation((Object[]) values);
        } else {
            return StringHelper.cleanQuotation(annotationValue.toString());
        }
        return Joiner.on(joiner).skipNulls().join(vs);
    }

    public static String getStringValue(AnnotationDesc annotationDesc, String name, String defaultValue) {
        String value = getStringValue(annotationDesc, name);
        return StringHelper.isNullThenSet(value, defaultValue);
    }

    private static List cleanQuotation(Object[] it) {
        List list = Lists.newArrayList(it);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, StringHelper.cleanQuotation(list.get(i).toString()));
        }
        return list;
    }

    private static List cleanQuotation(Iterable<?> it) {
        List list = Lists.newArrayList(it);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, StringHelper.cleanQuotation(list.get(i).toString()));
        }
        return list;
    }


}
