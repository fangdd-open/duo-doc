package com.fangdd.tp.doclet.helper;

import com.google.common.collect.Lists;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.FieldDoc;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @auth ycoe
 * @date 18/1/18
 */
public class RequestMappingAnnotationHelper {
    private static final Pattern VALUE_PATTERN = Pattern.compile("^\"(.*)\"$");
    public static List<String> getRequestMappingAnnotationValues(AnnotationDesc.ElementValuePair[] vs, String annotationName) {
        List<String> basePaths = Lists.newArrayList();
        for (AnnotationDesc.ElementValuePair elementValuePair : vs) {
            String name = elementValuePair.element().name();
            Object value = elementValuePair.value().value();
            if (annotationName.equals(name)) {
                AnnotationValue[] pathValues = (AnnotationValue[]) value;
                if (pathValues != null) {
                    for (AnnotationValue pathValue : pathValues) {
                        Object val = pathValue.value();
                        if(val instanceof String) {
                            basePaths.add(pathValue.value().toString());
//                            if( pathValue instanceof AnnotationValueImpl) {
//                            } else {
//                                System.out.println(pathValue);
//                            }
//                            String path = pathValue.toString();
//                            Matcher matcher = VALUE_PATTERN.matcher(path);
//                            if(matcher.find()) {
//                                basePaths.add(matcher.group(1));
//                            } else {
//                                basePaths.add(path);
//                            }
                        } else if(val instanceof FieldDoc){
                            basePaths.add(((FieldDoc) val).name());
                        }
                    }
                }
            }
        }
        return basePaths;
    }
}
