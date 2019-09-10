package com.fangdd.tp.doclet.analyser.dubbo;

import com.fangdd.tp.doclet.helper.BookHelper;
import com.fangdd.tp.doclet.pojo.DubboInfo;
import com.sun.javadoc.ClassDoc;

/**
 * @author xuwenzhen
 * @date 18/3/2
 */
public class XmlDubboServiceAnalyser {
    public static void analyse(ClassDoc classDoc) {
        String interfaceName = classDoc.qualifiedName();
        DubboInfo dubboInfo = BookHelper.getDubboInterface(interfaceName);
        DubboServiceInterfaceAnalyser.analyser(classDoc, dubboInfo);
    }
}
