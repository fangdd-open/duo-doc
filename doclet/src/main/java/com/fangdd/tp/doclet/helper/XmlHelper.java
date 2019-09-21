package com.fangdd.tp.doclet.helper;

import org.dom4j.Element;

/**
 * @author xuwenzhen
 * @date 18/3/2
 */
public class XmlHelper {
    public static Element getChildElement(Element element, String childName) {
        return element.element(childName);
    }

    public static String getChildElementText(Element element, String childrenName) {
        Element child = element.element(childrenName);
        if (child == null) {
            return null;
        }
        return child.getText();
    }
}
