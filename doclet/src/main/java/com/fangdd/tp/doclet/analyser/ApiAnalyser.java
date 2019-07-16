package com.fangdd.tp.doclet.analyser;

import com.fangdd.tp.doclet.DocletConfig;
import com.fangdd.tp.doclet.analyser.dubbo.DubboServiceImplAnalyser;
import com.fangdd.tp.doclet.analyser.dubbo.XmlDubboServiceAnalyser;
import com.fangdd.tp.doclet.analyser.rest.RestFulAnalyser;
import com.fangdd.tp.doclet.constant.DubboConstant;
import com.fangdd.tp.doclet.constant.SpringMvcConstant;
import com.fangdd.tp.doclet.helper.AnnotationHelper;
import com.fangdd.tp.doclet.helper.BookHelper;
import com.fangdd.tp.doclet.helper.Logger;
import com.fangdd.tp.doclet.helper.TagHelper;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Tag;

/**
 * @author ycoe
 * @date 18/1/9
 */
public class ApiAnalyser {
    private static final Logger logger = new Logger();

    public static void analyse(ClassDoc classDoc) {
        if (isAvailableRestApi(classDoc)) {
            logger.info("REST接口: " + classDoc);
            BookHelper.currentApiType = 0;
            RestFulAnalyser.analyse(classDoc);
        } else if (isAvailableDubboApi(classDoc)) {
            logger.info("Dubbo接口: " + classDoc);
            BookHelper.currentApiType = 1;
            DubboServiceImplAnalyser.analyse(classDoc);
        } else if (isXmlDubboApi(classDoc)) {
            BookHelper.currentApiType = 1;
            XmlDubboServiceAnalyser.analyse(classDoc);
        }
    }

    private static boolean isXmlDubboApi(ClassDoc classDoc) {
        String className = classDoc.qualifiedName();
        return BookHelper.getDubboInterface(className) != null;
    }

    private static boolean isAvailableDubboApi(ClassDoc classDoc) {
        AnnotationDesc serviceAnnotation = AnnotationHelper.getAnnotation(classDoc.annotations(), DubboConstant.ANNOTATION_ALIBABA_SERVICE);
        if (serviceAnnotation == null) {
            //尝试检查apache版本的
            serviceAnnotation = AnnotationHelper.getAnnotation(classDoc.annotations(), DubboConstant.ANNOTATION_APACHE_SERVICE);
        }
        return serviceAnnotation != null;
    }

    /**
     * 检查是否合法的Controller
     *
     * @param classDoc
     * @return
     */
    private static boolean isAvailableRestApi(ClassDoc classDoc) {
        BookHelper.requestMappingAnnotation = null;
        return checkByRestAnnotation(classDoc) || checkSuperClass(classDoc.superclass());

    }

    private static boolean checkSuperClass(ClassDoc superClass) {
        if (superClass == null || Object.class.getName().equals(superClass.toString())) {
            return false;
        }
        return checkByRestAnnotation(superClass);
    }

    private static boolean checkByRestAnnotation(ClassDoc classDoc) {
        //@RestController
        AnnotationDesc restControllerAnnotation = AnnotationHelper.getAnnotation(classDoc.annotations(), SpringMvcConstant.ANNOTATION_REST_CONTROLLER);
        if (restControllerAnnotation == null) {
            return false;
        }

        //@RequestMapping
        AnnotationDesc requestMappingAnnotation = AnnotationHelper.getAnnotation(classDoc.annotations(), SpringMvcConstant.ANNOTATION_REQUEST_MAPPING);
        if (requestMappingAnnotation == null) {
            return false;
        }

        // 检查是否添加了 @disable 标签
        Tag[] tags = classDoc.tags();
        if (TagHelper.contendTag(tags, DocletConfig.tagDisable)) {
            return false;
        }

        //添加进缓存
        BookHelper.requestMappingAnnotation = requestMappingAnnotation;
        return true;
    }
}
