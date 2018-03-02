package com.fangdd.tp.doclet.analyser.dubbo;

import com.fangdd.tp.doclet.exception.DocletException;
import com.fangdd.tp.doclet.helper.BookHelper;
import com.fangdd.tp.doclet.pojo.DubboInfo;
import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.util.List;

/**
 * @auth ycoe
 * @date 18/1/10
 */
public class DubboXmlAnalyser {

    public static void analyse(File dubboXmlFile) {
        SAXBuilder jdomBuilder = new SAXBuilder();
        Document jdomDocument;
        try {
            jdomDocument = jdomBuilder.build(dubboXmlFile);
        } catch (Exception e) {
            throw new DocletException("解析pom.xml出错！", e);
        }
        Element beans = jdomDocument.getRootElement();
        List<Content> contentList = beans.getContent();
        for (Content content : contentList) {
            if (content instanceof Element) {
                Element dubboServiceNode = (Element) content;
                if (!"service".equals(dubboServiceNode.getName())) {
                    continue;
                }
                String interfaceClass = dubboServiceNode.getAttribute("interface").getValue();
                String version = "";
                Attribute versionAttribute = dubboServiceNode.getAttribute("version");
                if (versionAttribute != null) {
                    version = versionAttribute.getValue();
                }
                DubboInfo dubboInfo = new DubboInfo();
                dubboInfo.setVersion(version == null ? "" : version);

                BookHelper.addDubboInterface(interfaceClass, dubboInfo);
            }
        }
    }
}
