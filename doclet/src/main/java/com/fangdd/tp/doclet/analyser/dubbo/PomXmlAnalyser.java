package com.fangdd.tp.doclet.analyser.dubbo;

import com.fangdd.tp.doclet.exception.DocletException;
import com.fangdd.tp.doclet.helper.XmlHelper;
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
        String artifactId = XmlHelper.getChildElementText(project, "artifactId");
        String version = XmlHelper.getChildElementText(project, "version");
        String groupId = XmlHelper.getChildElementText(project, "groupId");
        String name = XmlHelper.getChildElementText(project, "name");
        String description = XmlHelper.getChildElementText(project, "description");
        if (Strings.isNullOrEmpty(groupId)) {
            //尝试从parent中获取
            Element parentElement = XmlHelper.getChildElement(project, "parent");
            groupId = XmlHelper.getChildElementText(parentElement, "groupId");
        }

        if(Strings.isNullOrEmpty(version)) {
            //尝试从parent中获取
            Element parentElement = XmlHelper.getChildElement(project, "parent");
            version = XmlHelper.getChildElementText(parentElement, "version");
        }
        info.setId(groupId + ":" + artifactId);
        info.setArtifactId(artifactId);
        info.setVersion(version);
        info.setGroupId(groupId);
        info.setName(name);
        info.setDescription(description);
        return info;
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
