package com.fangdd.tp.doclet;

import com.fangdd.tp.doclet.analyser.ApiAnalyser;
import com.fangdd.tp.doclet.analyser.dubbo.PomXmlAnalyser;
import com.fangdd.tp.doclet.export.ExportMarkdownToConsole;
import com.fangdd.tp.doclet.export.ExportToMongoDB;
import com.fangdd.tp.doclet.helper.BookHelper;
import com.fangdd.tp.doclet.pojo.Artifact;
import com.fangdd.tp.doclet.pojo.Chapter;
import com.fangdd.tp.doclet.render.EntityHandle;
import com.google.common.collect.Lists;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @auth ycoe
 * @date 18/1/5
 */
public class TpDoclet extends Doclet {
    public static boolean start(RootDoc root) {
        String baseDir = System.getProperty("basedir", "/Users/ycoe/Projects/fdd/tp/tp-doc/tp-demo-server");
        //下面在开发测试时把注释打开
//        String exportType = System.getProperty("exportType", "console");
//        String server = System.getProperty("server", "http://127.0.0.1:17010");

        String exportType = System.getProperty("exportType", "web");
        String server = System.getProperty("server", "http://10.0.1.86:17010");
        BookHelper.setServer(server);

        //读取主服务的信息
        File pomFile = new File(baseDir + "/pom.xml");
        if (pomFile.exists()) {
            Artifact artifact = PomXmlAnalyser.analyse(pomFile);
            if (artifact != null) {
                BookHelper.setArtifact(artifact);
            }
        }

        for (ClassDoc doc : root.classes()) {
            ApiAnalyser.analyse(doc);
        }

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

    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }
}
