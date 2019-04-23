package com.fangdd.tp.doclet;

import com.fangdd.tp.doclet.analyser.ApiAnalyser;
import com.fangdd.tp.doclet.analyser.MarkdownAnalyser;
import com.fangdd.tp.doclet.analyser.dubbo.DubboXmlAnalyser;
import com.fangdd.tp.doclet.analyser.dubbo.PomXmlAnalyser;
import com.fangdd.tp.doclet.exporter.Exporter;
import com.fangdd.tp.doclet.exporter.impl.ConsoleMarkdownExporter;
import com.fangdd.tp.doclet.exporter.impl.ServerExporter;
import com.fangdd.tp.doclet.helper.BookHelper;
import com.fangdd.tp.doclet.helper.DocHelper;
import com.fangdd.tp.doclet.helper.Logger;
import com.fangdd.tp.doclet.pojo.Artifact;
import com.fangdd.tp.doclet.pojo.Chapter;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author ycoe
 * @date 18/1/5
 */
public class TpDoclet extends Doclet {
    private static final Logger logger = new Logger();

    public static boolean start(RootDoc root) {
        //读取主服务的信息
        File pomFile = new File(DocletConfig.baseDir + "/pom.xml");
        if (pomFile.exists()) {
            Artifact artifact = PomXmlAnalyser.analyse(pomFile);
            if (artifact != null) {
                BookHelper.setArtifact(artifact);
            }
        }

        // 从xml配置中读取dubbo配置
        loadDubboFromXmlConf(DocletConfig.baseDir);

        for (ClassDoc doc : root.classes()) {
            ApiAnalyser.analyse(doc);
        }

        //尝试读取主服务根目录下的.md文件
        MarkdownAnalyser.readMarkdown();

        List<Chapter> chapterSet = Lists.newArrayList();
        for (Map.Entry<String, Chapter> entry : BookHelper.getBooks().entrySet()) {
            chapterSet.add(entry.getValue());
        }

        //重新排序
        BookHelper.sort(chapterSet);

        //导出
        Exporter exporter = DocHelper.getExporter();
        if (exporter != null) {
            return exporter.export(chapterSet);
        } else {
            logger.info("未指定或无法找到对应的导出方式：exporter=" + DocletConfig.exporter);
        }

        return true;
    }

    private static void loadDubboFromXmlConf(String baseDir) {
        //尝试读取xml配置的dubbo服务配置
        Iterable<String> dubboConfXmlArray = Splitter
                .on(",")
                .omitEmptyStrings()
                .trimResults()
                .split(DocletConfig.dubboConfXmls);

        for (String dubboConfXml : dubboConfXmlArray) {
            File dubboXmlFile = new File(baseDir + "/src/main/resources/" + dubboConfXml);
            if (dubboXmlFile.exists()) {
                DubboXmlAnalyser.analyse(dubboXmlFile);
            }
        }
    }

    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }
}
