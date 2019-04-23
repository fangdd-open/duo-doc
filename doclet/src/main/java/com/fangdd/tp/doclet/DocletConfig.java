package com.fangdd.tp.doclet;

/**
 * Created by xuwenzhen on 2019/3/7.
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

    static {
        baseDir = System.getProperty("basedir", "/Users/ycoe/Projects/fdd/tp/tp-doc/doclet-test");
        exporter = System.getProperty("exporter", "server");
        appId = System.getProperty("appId");
        server = System.getProperty("server", "http://tp-doc.fangdd.net");
        dubboConfXmls = System.getProperty("dubboXmlConfigs", "applicationContext-dubbo.xml");
        markdownDir = System.getProperty("markdownDir", "doc");
        commitId = System.getProperty("commitId", "");
        if (markdownDir.startsWith("/")) {
            markdownDir = markdownDir.substring(1);
        }
        if (markdownDir.startsWith("./")) {
            markdownDir = markdownDir.substring(2);
        }
    }
}
