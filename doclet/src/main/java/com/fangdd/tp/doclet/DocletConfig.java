package com.fangdd.tp.doclet;

import java.io.File;

/**
 * TP-DOC配置
 *
 * @author xuwenzhen
 * @date 2019/3/7
 */
public class DocletConfig {
    /**
     * 项目目录
     */
    public static String baseDir;

    /**
     * appId
     */
    public static String appId;

    /**
     * 生成的文档导出到哪里，可选：mongodb | console
     */
    public static String exporter;

    /**
     * 当前文档的版本号
     */
    public static String commitId;

    /**
     * 文档服务地址
     */
    public static String server;

    /**
     * 动态配置的dubboXml配置文件，多个使用逗号分隔
     */
    public static String dubboConfXmls;

    /**
     * markdown文档目录
     */
    public static String markdownDir;

    /**
     * chapter
     */
    public static String tagChapter = "@chapter";

    /**
     * section
     */
    public static String tagSection = "@section";

    /**
     * api
     */
    public static String tagApi = "@api";

    /**
     * chapter排序
     */
    public static String tagChapterSort = "@c1";

    /**
     * section排序
     */
    public static String tagSectionSort = "@c2";

    /**
     * api排序
     */
    public static String tagApiSort = "@c3";

    /**
     * 自XXX起
     */
    public static String tagSince = "@since";

    /**
     * 作者
     */
    public static String tagAuthor = "@author";

    /**
     * 废弃
     */
    public static String tagDeprecated = "@deprecated";

    /**
     * 返回
     */
    public static String tagReturn = "@return";

    /**
     * 禁止
     */
    public static String tagDisable = "@disable";

    /**
     * 必填
     */
    public static String tagRequired = "@required";

    /**
     * Demo值
     */
    public static String tagDemo = "@demo";

    private static final String CURRENT_DIR = "./";

    static {
        baseDir = System.getProperty("basedir", "/Users/ycoe/Projects/fdd/tp/tp-doc/doclet-test");
        exporter = System.getProperty("exporter", "server");
        appId = System.getProperty("appId");
        server = System.getProperty("server", "http://tp-doc.fangdd.net");
        dubboConfXmls = System.getProperty("dubboXmlConfigs", "applicationContext-dubbo.xml");
        markdownDir = System.getProperty("markdownDir", "doc");
        commitId = System.getProperty("commitId", "");
        if (markdownDir.startsWith(File.separator)) {
            markdownDir = markdownDir.substring(1);
        }
        if (markdownDir.startsWith(CURRENT_DIR)) {
            markdownDir = markdownDir.substring(2);
        }
    }
}
