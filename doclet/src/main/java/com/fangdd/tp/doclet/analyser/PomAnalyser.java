package com.fangdd.tp.doclet.analyser;

import com.fangdd.tp.doclet.DocletConfig;
import com.fangdd.tp.doclet.analyser.dubbo.PomXmlAnalyser;
import com.fangdd.tp.doclet.helper.BookHelper;
import com.fangdd.tp.doclet.pojo.Artifact;

import java.io.File;

/**
 * maven pom.xml文件解析
 * @author xuwenzhen
 */
public class PomAnalyser {
    public static void analyse() {
        File pomFile = new File(DocletConfig.baseDir + "/pom.xml");
        if (pomFile.exists()) {
            Artifact artifact = PomXmlAnalyser.analyse(pomFile);
            if (artifact != null) {
                BookHelper.setArtifact(artifact);
            }
        }
    }
}
