package com.fangdd.tp.doclet.analyser.dubbo;

import com.fangdd.tp.doclet.exception.DocletException;
import com.fangdd.tp.doclet.pojo.Artifact;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.javadoc.ClassDoc;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.Map;

/**
 * @auth ycoe
 * @date 18/1/10
 */
public class PomXmlAnalyser {
    private static final Map<File, Artifact> PATH_POM_MAP = Maps.newHashMap();
    private static final List<File> pomPathList = Lists.newArrayList();

    public static Artifact analyse(ClassDoc classDoc) {
        File classFile = classDoc.position().file();
        if (!classFile.exists()) {
            throw new DocletException("java文件：" + classDoc.qualifiedName() + "的源码未配置进来！请在pom.xml 自动化文档的plugin > configuration > sourcepath元素里面添加源文件路径，多个路径使用冒号分隔");
        }
        Artifact artifact = PATH_POM_MAP.get(classFile);
        if (artifact == null) {
            //尝试获取接口文件对应的pom.xml
            pomPathList.clear();
            artifact = getPomXml(classFile.getParentFile());
        }
        return artifact;
    }

    public static Artifact analyse(File file) {
        Artifact info = new Artifact();
        SAXBuilder jdomBuilder = new SAXBuilder();
        Document jdomDocument;
        try {
            jdomDocument = jdomBuilder.build(file);
        } catch (Exception e) {
            throw new DocletException("解析pom.xml出错！", e);
        }
        Element project = jdomDocument.getRootElement();
        String artifactId = getChildElementText(project, "artifactId");
        String version = getChildElementText(project, "version");
        String groupId = getChildElementText(project, "groupId");
        String name = getChildElementText(project, "name");
        String description = getChildElementText(project, "description");
        if (Strings.isNullOrEmpty(groupId)) {
            //尝试从parent中获取
            Element parentElement = getChildElement(project, "parent");
            groupId = getChildElementText(parentElement, "groupId");
        }

        if(Strings.isNullOrEmpty(version)) {
            //尝试从parent中获取
            Element parentElement = getChildElement(project, "parent");
            version = getChildElementText(parentElement, "version");
        }
        info.setId(groupId + ":" + artifactId);
        info.setArtifactId(artifactId);
        info.setVersion(version);
        info.setGroupId(groupId);
        info.setName(name);
        info.setDescription(description);
        return info;
    }

    private static Element getChildElement(Element element, String childName) {
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

    private static String getChildElementText(Element element, String childrenName) {
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

    private static Artifact getPomXml(File file) {
//        logger.info("尝试寻找pom.xml at {}", file.getPath());
        pomPathList.add(file);
        File[] pomXmlFiles = file.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return "pom.xml".equals(pathname.getName());
            }
        });
        if (pomXmlFiles != null && pomXmlFiles.length == 1) {
            System.out.println("找到" + pomXmlFiles[0].getAbsolutePath());
            Artifact info = PomXmlAnalyser.analyse(pomXmlFiles[0]);
            for (File path : pomPathList) {
                PATH_POM_MAP.put(path, info);
            }
            return info;
        }
        File parent = file.getParentFile();
        if ("/".equals(parent.getPath())) {
            return null;
        }
        return getPomXml(parent);
    }
}
