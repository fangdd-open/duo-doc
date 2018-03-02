package com.fangdd.tp.doclet.helper;

import com.fangdd.tp.doclet.exception.DocletException;
import org.jdom2.Element;
import org.jdom2.filter.Filters;

import java.util.List;

/**
 * @auth ycoe
 * @date 18/3/2
 */
public class XmlHelper {
    public static Element getChildElement(Element element, String childName) {
        Element parentElement = null;
        List<Element> contents = element.getContent(Filters.element());
        for (Element el : contents) {
            if (el.getName().equals(childName)) {
                parentElement = el;
                break;
            }
        }
        if (parentElement == null) {
            throw new DocletException("未找到<groupId>!");
        }
        return parentElement;
    }

    public static String getChildElementText(Element element, String childrenName) {
        List<Element> es = element.getContent(Filters.element());
        if (es == null || es.isEmpty()) {
            return null;
        }
        for (Element e : es) {
            if (e.getName().equals(childrenName)) {
                return e.getTextTrim();
            }
        }
        return null;
    }
}
