package com.fangdd.tp.doclet.analyser;

import com.fangdd.tp.doclet.analyser.dubbo.DubboServiceImplAnalyser;
import com.fangdd.tp.doclet.analyser.rest.RestFulAnalyser;
import com.fangdd.tp.doclet.constant.DubboConstant;
import com.fangdd.tp.doclet.constant.SpringMvcConstant;
import com.fangdd.tp.doclet.helper.AnnotationHelper;
import com.fangdd.tp.doclet.helper.Logger;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;

/**
 * @auth ycoe
 * @date 18/1/9
 */
public class ApiAnalyser {
    private static final Logger logger = new Logger();

    public static void analyse(ClassDoc classDoc) {
        if (isAvailableRestApi(classDoc)) {
            logger.info("REST接口: " + classDoc);
            RestFulAnalyser.analyse(classDoc);
        } else if (isAvailableDubboApi(classDoc)) {
            logger.info("Dubbo接口: " + classDoc);
            DubboServiceImplAnalyser.analyse(classDoc);
        }
    }

    private static boolean isAvailableDubboApi(ClassDoc classDoc) {
        AnnotationDesc serviceAnnotation = AnnotationHelper.getAnnotation(classDoc.annotations(), DubboConstant.ANNOTATION_SERVICE);
        return serviceAnnotation != null;
    }

    /**
     * 检查是否合法的Controller
     *
     * @param classDoc
     * @return
     */
    private static boolean isAvailableRestApi(ClassDoc classDoc) {
        //@RestController
        AnnotationDesc restControllerAnnotation = AnnotationHelper.getAnnotation(classDoc.annotations(), SpringMvcConstant.ANNOTATION_REST_CONTROLLER);
        //@Controller
//        AnnotationDesc controllerAnnotation = AnnotationHelper.getAnnotation(classDoc.annotations(), SpringMvcConstant.ANNOTATION_CONTROLLER);
        if (restControllerAnnotation == null) {
            return false;
        }

        //@RequestMapping
        AnnotationDesc requestMappingAnnotation = AnnotationHelper.getAnnotation(classDoc.annotations(), SpringMvcConstant.ANNOTATION_REQUEST_MAPPING);
        if (requestMappingAnnotation == null) {
            return false;
        }
        return true;
    }
}
