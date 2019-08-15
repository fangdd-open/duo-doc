package com.fangdd.tp.doclet.analyser.entity;

import com.fangdd.tp.doclet.constant.EntityConstant;
import com.fangdd.tp.doclet.helper.AnnotationHelper;
import com.fangdd.tp.doclet.pojo.EntityRef;
import com.google.common.base.Strings;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.FieldDoc;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ycoe
 * @date 18/2/11
 */
public class DateTimeFormatFieldAnnotationAnalyser extends EntityFieldAnnotationAnalyser {
    @Override
    public boolean check(EntityRef fieldRef, FieldDoc fieldDoc) {
        return Strings.isNullOrEmpty(fieldRef.getDemo());
    }

    @Override
    public boolean analyse(AnnotationDesc dateTimeFormatAnnotation, EntityRef fieldRef, FieldDoc fieldDoc) {
        String pattern = null;
        String iso = AnnotationHelper.getStringValue(dateTimeFormatAnnotation, "iso");
        if (EntityConstant.DATE_TIME_FORMAT_ISO_DATE.equals(iso)) {
            pattern = "yyyy-MM-dd";
        } else if (EntityConstant.DATE_TIME_FORMAT_ISO_TIME.equals(iso)) {
            pattern = "HH:mm:ss.SSSZ";
        } else if (EntityConstant.DATE_TIME_FORMAT_ISO_DATE_TIME.equals(iso)) {
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        }

        String patternVal = AnnotationHelper.getStringValue(dateTimeFormatAnnotation, "pattern");
        if (!Strings.isNullOrEmpty(patternVal)) {
            pattern = patternVal;
        }
        if (!Strings.isNullOrEmpty(pattern)) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            fieldRef.setDemo(sdf.format(new Date()));
        }
        return true;
    }
}
