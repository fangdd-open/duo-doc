package com.fangdd.tp.doclet.analyser;

import com.fangdd.tp.doclet.DocletConfig;
import com.fangdd.tp.doclet.helper.BookHelper;
import com.fangdd.tp.doclet.helper.FileHelper;
import com.fangdd.tp.doclet.helper.Logger;
import com.google.common.base.Strings;

import java.io.File;

/**
 * @author xuwenzhen
 * @date 2019/3/11
 */
public class MarkdownAnalyser {
    private static final Logger logger = new Logger();
    private static final String MD = ".md";
    private static final String PATH_SPLITER = "/";

    public static void readMarkdown() {

        File readme = new File(DocletConfig.baseDir, "README.md");
        if (readme.exists()) {
            readMarkdownDoc(readme, PATH_SPLITER);
        }
        File baseDir = new File(DocletConfig.baseDir, DocletConfig.markdownDir);
        if (!baseDir.exists()) {
            return;
        }

        readMarkdown(baseDir, PATH_SPLITER + DocletConfig.markdownDir + PATH_SPLITER);
    }

    private static void readMarkdown(File baseDir, String path) {
        File[] files = baseDir.listFiles();

        if (files == null || files.length == 0) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                //如果是目录
                readMarkdown(file, path + file.getName() + PATH_SPLITER);
                continue;
            }
            if (file.getName().toLowerCase().endsWith(MD)) {
                //读取概要
                readMarkdownDoc(file, path);
            }
        }
    }

    private static void readMarkdownDoc(File markdownFile, String path) {
        String markdownFileName = markdownFile.getName();
        markdownFileName = markdownFileName.substring(0, markdownFileName.length() - 3);
        String markdown = FileHelper.readFileToString(markdownFile);
        if (!Strings.isNullOrEmpty(markdown)) {
            BookHelper.addMarkdownDoc(path + markdownFileName, markdown);
            logger.info("MD：[" + path + markdownFileName + "]" + markdownFile.getAbsolutePath());
        }
    }
}
