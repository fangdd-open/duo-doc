package com.fangdd.tp.doclet.exporter.impl;

import com.alibaba.fastjson.JSONObject;
import com.fangdd.tp.doclet.constant.DocletConstant;
import com.fangdd.tp.doclet.exporter.Exporter;
import com.fangdd.tp.doclet.helper.BookHelper;
import com.fangdd.tp.doclet.pojo.Artifact;
import com.fangdd.tp.doclet.pojo.Chapter;
import com.fangdd.tp.doclet.pojo.DocDto;
import com.fangdd.tp.doclet.pojo.Entity;
import com.fangdd.tp.doclet.render.EntityHandle;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 *
 * @author xuwenzhen
 * @date 2019/7/23
 */
public abstract class BaseApiJsonExporter implements Exporter {
    protected String toJsonString(List<Chapter> chapterSet) {
        List<Entity> entitySet = Lists.newArrayList();
        for (Map.Entry<String, Entity> entry : EntityHandle.getEntityMap().entrySet()) {
            entitySet.add(entry.getValue());
        }

        DocDto request = new DocDto();
        Artifact artifact = BookHelper.getArtifact();
        request.setArtifact(artifact);
        request.setChapters(chapterSet);
        request.setEntities(entitySet);
        request.setDocletVersion(DocletConstant.DOCLET_VERTION);

        Map<String, String> markdownMap = BookHelper.getMarkdownDocMap();
        if (!markdownMap.isEmpty()) {
            request.setMarkdownMaps(markdownMap);
        }

        return JSONObject.toJSONString(request);
    }
}
