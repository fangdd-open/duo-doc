package com.fangdd.tp.doclet.analyser.dubbo;

import com.fangdd.tp.doclet.DocletConfig;
import com.google.common.base.Splitter;

import java.io.File;

/**
 * 读取Dubbo的xml配置文件
 * @author xuwenzhen
 * @date 2019/7/22
 */
public class DubboConfigureXmlAnalyser {

    private static final String SEPARATOR = ",";

    public static void loadDubboFromXmlConf(String baseDir) {
        //尝试读取xml配置的dubbo服务配置
        Iterable<String> dubboConfXmlArray = Splitter
                .on(SEPARATOR)
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
}
