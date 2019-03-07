package com.fangdd.tp.doclet.analyser.dubbo;

import com.fangdd.tp.doclet.pojo.Api;
import com.fangdd.tp.doclet.pojo.Artifact;
import com.fangdd.tp.doclet.pojo.DubboInfo;
import com.fangdd.tp.doclet.pojo.Section;
import com.fangdd.tp.doclet.helper.BaseApiInfoHelper;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;

/**
 * 分析Dubbo接口
 *
 * @author ycoe
 * @date 18/1/9
 */
public class DubboServiceInterfaceAnalyser {
    public static Section analyser(ClassDoc classDoc, DubboInfo dubboInfo) {
        Section section = ChapterDubboAnalyser.analyse(classDoc);
        Artifact artifact = PomXmlAnalyser.analyse(classDoc);

        MethodDoc[] methods = classDoc.methods();
        for (MethodDoc method : methods) {
            analyseApi(method, section, artifact, dubboInfo);
        }

        return section;
    }

    private static void analyseApi(MethodDoc method, Section section, Artifact artifact, DubboInfo dubboInfo) {
        Api api = BaseApiInfoHelper.getApiBase(method, section);
        api.setVersion(dubboInfo.getVersion());
        api.setArtifact(artifact);
        api.setDubboInfo(dubboInfo);
    }
}
