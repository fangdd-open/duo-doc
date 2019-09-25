package com.fangdd.tp.doclet.exporter.impl;

import com.alibaba.fastjson.JSONObject;
import com.fangdd.tp.doclet.DocletConfig;
import com.fangdd.tp.doclet.exporter.Exporter;
import com.fangdd.tp.doclet.helper.CollectionUtils;
import com.fangdd.tp.doclet.helper.Logger;
import com.fangdd.tp.doclet.pojo.*;
import com.fangdd.tp.doclet.render.EntityHandle;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 导出到classes目录的api.json文件
 *
 * @author xuwenzhen
 * @date 2019-7-23
 */
public class ApiJson4GraphQLExporter implements Exporter {
    private static final Logger logger = new Logger();
    private static final String EXPORTER_NAME = "graphql";
    private static final int MIN_DOC_LEN = 1000;

    @Override
    public String exporterName() {
        return EXPORTER_NAME;
    }

    @Override
    public boolean export(List<Chapter> chapterSet) {
        String docData = toJsonString(chapterSet);

        if (docData.length() < MIN_DOC_LEN) {
            logger.error("当前文档内容长度过小，请检查是否正常：length=" + docData.length(), null);
            return false;
        }
        File outputDir = new File(DocletConfig.outputDirectory);
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            logger.error("创建输出目录失败：" + DocletConfig.outputDirectory, null);
            return false;
        }
        File file = new File(DocletConfig.outputDirectory + File.separator + DocletConfig.apiJson);
        try {
            Files.asCharSink(file, Charsets.UTF_8).write(docData);
        } catch (IOException e) {
            logger.error("写入文件失败：" + file.getAbsolutePath(), e);
        }
        return true;
    }

    private String toJsonString(List<Chapter> chapterSet) {
        List<Entity> entitySet = Lists.newArrayList();
        for (Map.Entry<String, Entity> entry : EntityHandle.getEntityMap().entrySet()) {
            entitySet.add(entry.getValue());
        }

        List<Api> apis = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(chapterSet)) {
            for(Chapter chapter : chapterSet) {
                List<Section> sections = chapter.getSections();
                if(CollectionUtils.isEmpty(sections)) {
                    continue;
                }
                for (Section section : sections) {
                    if (CollectionUtils.isEmpty(section.getApis())) {
                        continue;
                    }
                    apis.addAll(section.getApis());
                }
            }
        }

        ProviderApiDto providerApiDto = new ProviderApiDto();
        providerApiDto.setEntities(entitySet);
        providerApiDto.setApis(apis);
        return JSONObject.toJSONString(providerApiDto);
    }
}
