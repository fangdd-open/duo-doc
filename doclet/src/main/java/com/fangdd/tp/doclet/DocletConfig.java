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
     * 生成的文档导出到哪里，可选：mongodb | console
     */
    public static String exportType;

    /**
     * 文档服务地址
     */
    public static String server;

    /**
     * 动态配置的dubboXml配置文件，多个使用逗号分隔
     */
    public static String dubboConfXmls;

    static {
        baseDir = System.getProperty("basedir", "/Users/ycoe/Projects/fdd/tp/tp-doc/doclet-test");
        exportType = System.getProperty("exportType", "mongodb");
        server = System.getProperty("server", "http://tp-doc.fangdd.net");
        dubboConfXmls = System.getProperty("dubbo-xml", "applicationContext-dubbo.xml");
    }
}
