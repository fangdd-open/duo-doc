package com.fangdd.tp.doclet.analyser.dubbo;

import com.fangdd.tp.doclet.exception.DocletException;
import com.fangdd.tp.doclet.helper.BookHelper;
import com.fangdd.tp.doclet.pojo.DubboInfo;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

/**
 * @author xuwenzhen
 * @date 18/1/10
 */
public class DubboXmlAnalyser {
    public static void analyse(File dubboXmlFile) {
        SAXReader saxReader = new SAXReader();
        Document jdomDocument = null;
        try {
            jdomDocument = saxReader.read(dubboXmlFile);
        } catch (DocumentException e) {
            throw new DocletException("解析pom.xml出错！" + dubboXmlFile.getAbsolutePath(), e);
        }

        Element root = jdomDocument.getRootElement();
        List<Element> contentList = root.elements();
        for (Element content : contentList) {
            Element dubboServiceNode = content;
            if (!"service".equals(dubboServiceNode.getName())) {
                continue;
            }
            String interfaceClass = dubboServiceNode.attributeValue("interface");
            String version = "";
            Attribute versionAttribute = dubboServiceNode.attribute("version");
            if (versionAttribute != null) {
                version = versionAttribute.getValue();
            }
            DubboInfo dubboInfo = new DubboInfo();
            dubboInfo.setVersion(version == null ? "" : version);

            BookHelper.addDubboInterface(interfaceClass, dubboInfo);
        }
    }
}
