package com.fangdd.tp.doclet;

import com.fangdd.tp.doclet.analyser.ApiAnalyser;
import com.fangdd.tp.doclet.analyser.dubbo.DubboXmlAnalyser;
import com.fangdd.tp.doclet.analyser.dubbo.PomXmlAnalyser;
import com.fangdd.tp.doclet.export.ExportMarkdownToConsole;
import com.fangdd.tp.doclet.export.ExportToMongoDB;
import com.fangdd.tp.doclet.helper.BookHelper;
import com.fangdd.tp.doclet.helper.FileHelper;
import com.fangdd.tp.doclet.pojo.Artifact;
import com.fangdd.tp.doclet.pojo.Chapter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.Map;

/**
 * @auth ycoe
 * @date 18/1/5
 */
public class TpDoclet extends Doclet {
    public static boolean start(RootDoc root) {
        String baseDir = System.getProperty("basedir", "/Users/ycoe/Projects/fdd/tp/tp-doc/doclet-test");
        //下面在开发测试时把注释打开`
//        String exportType = System.getProperty("exportType", "mongodb");
//        String exportType = System.getProperty("exportType", "web");
        String exportType = System.getProperty("exportType", "console"); //在控制台打印，用于测试
//        String server = System.getProperty("server", "http://127.0.0.1:17010"); //提交到本地，用于测试

        String server = System.getProperty("server", "http://tp-doc.fangdd.net");
        BookHelper.setServer(server);

        //读取主服务的信息
        File pomFile = new File(baseDir + "/pom.xml");
        if (pomFile.exists()) {
            Artifact artifact = PomXmlAnalyser.analyse(pomFile);
            if (artifact != null) {
                BookHelper.setArtifact(artifact);
            }
        }

        //尝试读取xml配置的dubbo服务配置
        File dubboXmlFile = new File(baseDir + "/src/main/resources/applicationContext-dubbo.xml");
        if (dubboXmlFile.exists()) {
            DubboXmlAnalyser.analyse(dubboXmlFile);
        }

        for (ClassDoc doc : root.classes()) {
            ApiAnalyser.analyse(doc);
        }

        //尝试读取主服务根目录下的.md文件
        readMarkdown(new File(baseDir));

        if ("console".equalsIgnoreCase(exportType)) {
            //导出 markdown 到 控制台
            List<Chapter> chapterSet = Lists.newArrayList();
            for (Map.Entry<String, Chapter> entry : BookHelper.getBooks().entrySet()) {
                chapterSet.add(entry.getValue());
            }
            ExportMarkdownToConsole.export(chapterSet);
        } else {
            //导出到MongoDB
            ExportToMongoDB.export();
        }

        return true;
    }

    private static void readMarkdown(File baseDir) {
        if (!baseDir.exists()) {
            return;
        }

        File[] markdownFiles = baseDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".md");
            }
        });
        if (markdownFiles == null || markdownFiles.length == 0) {
            return;
        }

        for (File markdown : markdownFiles) {
            //读取概要
            readMarkdownDoc(markdown);
        }
    }

    private static void readMarkdownDoc(File markdownFile) {
        String markdownFileName = markdownFile.getName();
        markdownFileName = markdownFileName.substring(0, markdownFileName.length() - 3);
        String markdown = FileHelper.readFileToString(markdownFile);
        if (!Strings.isNullOrEmpty(markdown)) {
            BookHelper.addMarkdownDoc(markdownFileName, markdown);
            System.out.println("读取markdown文件：[" + markdownFileName + "]" + markdownFile.getAbsolutePath());
        }
    }

    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }
}
