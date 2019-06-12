package com.fangdd.tp.doclet.analyser.dubbo;

import com.fangdd.tp.doclet.constant.DubboConstant;
import com.fangdd.tp.doclet.helper.AnnotationHelper;
import com.fangdd.tp.doclet.pojo.Chapter;
import com.fangdd.tp.doclet.pojo.DubboInfo;
import com.fangdd.tp.doclet.pojo.Section;
import com.google.common.base.Strings;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ycoe
 * @date 18/1/9
 */
public class DubboServiceImplAnalyser {
    private static final Pattern VALUE_PATTERN = Pattern.compile("^\"(.*)\"$");
    public static void analyse(ClassDoc classDoc) {
        AnnotationDesc serviceAnnotation = AnnotationHelper.getAnnotation(classDoc.annotations(), DubboConstant.ANNOTATION_SERVICE);
        AnnotationDesc.ElementValuePair[] vs = serviceAnnotation.elementValues();
        DubboInfo dubboInfo = new DubboInfo();
        for (AnnotationDesc.ElementValuePair elementValuePair : vs) {
            String name = elementValuePair.element().name();
            Object value = elementValuePair.value().value();
            setDubboInfo(dubboInfo, name, value);
        }

        Chapter chapter = null;
        ClassDoc[] interfaces = classDoc.interfaces();
        for (ClassDoc parent : interfaces) {
            //拆到每个接口
            Section section = DubboServiceInterfaceAnalyser.analyser(parent, dubboInfo);
            if (section == null) {
                continue;
            }
            if(chapter == null) {
                chapter = section.getChapter();
            }
        }

        //读取实现类的接口说明



    }

    /**
     * 注解值
     *
     * @param dubboInfo
     * @param tagName
     * @param tagValue
     */
    private static void setDubboInfo(DubboInfo dubboInfo, String tagName, Object tagValue) {
        if ("version".equals(tagName)) {
            //版本号
            String version = tagValue.toString();
            Matcher matcher = VALUE_PATTERN.matcher(version);
            if(matcher.find()) {
                version = matcher.group(1);
            }
            dubboInfo.setVersion(version);
        }
    }
}
