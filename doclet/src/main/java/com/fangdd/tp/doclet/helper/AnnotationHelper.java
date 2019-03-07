package com.fangdd.tp.doclet.helper;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationValue;

/**
 * @author ycoe
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

    public static String getStringValue(AnnotationDesc annotationDesc, String name, String defaultValue) {
        String value = getStringValue(annotationDesc, name);
        return StringHelper.isNullThenSet(value, defaultValue);
    }
}
