package com.fangdd.tp.service.impl;

import com.fangdd.tp.dao.ApiDao;
import com.fangdd.tp.dao.DocChapterDao;
import com.fangdd.tp.doclet.pojo.Api;
import com.fangdd.tp.doclet.pojo.Artifact;
import com.fangdd.tp.doclet.pojo.Chapter;
import com.fangdd.tp.doclet.pojo.Section;
import com.fangdd.tp.dto.request.DocQuery;
import com.fangdd.tp.entity.ApiEntity;
import com.fangdd.tp.service.ApiUnwindService;
import com.fangdd.tp.service.DocService;
import com.fangdd.traffic.common.mongo.utils.UUIDUtils;
import com.google.common.collect.Lists;
import com.mongodb.client.model.DeleteManyModel;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertOneModel;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ycoe
 * @date 19/1/21
 */
@Service
public class ApiUnwindServiceImpl implements ApiUnwindService {
    private static final Logger logger = LoggerFactory.getLogger(ApiUnwindServiceImpl.class);
    private static final String DOC_ID = "docId";
    private static final String DOC_VERSION = "docVersion";

    @Autowired
    private DocService docService;

    @Autowired
    private DocChapterDao docChapterDao;

    @Autowired
    private ApiDao apiDao;

    @Async
    @Override
    public void unwindAll() {
        DocQuery query = new DocQuery();
        List<Artifact> docs = docService.getDocList(query);
        docs.forEach(doc -> unwindDocApi(doc.getId(), doc.getDocVersion()));
    }

    @Override
    public void unwindDocApi(String docId, Long docVersion) {
        logger.info("准备抽取：{}", docId);
        Bson versionFilter = Filters.and(
                Filters.eq(DOC_ID, docId),
                Filters.eq(DOC_VERSION, docVersion)
        );
        List<Chapter> chapters = docChapterDao
                .find(versionFilter)
                .into(Lists.newArrayList());

        List bulkWriteList = Lists.newArrayList();
        //删除整个文档
        bulkWriteList.add(new DeleteManyModel(Filters.eq(DOC_ID, docId)));
        chapters.forEach(chapter -> {
            List<Section> sections = chapter.getSections();
            if (sections == null) {
                return;
            }
            sections.forEach(section -> {
                List<Api> apis = section.getApis();
                if (apis == null) {
                    return;
                }
                apis.forEach(api -> addApi(bulkWriteList, chapter, section, api, docId));
            });
        });
        logger.info("抽取：{}，数量：{}", docId, bulkWriteList.size() - 1);
        apiDao.bulkWrite(bulkWriteList);
    }

    private void addApi(List bulkWriteList, Chapter chapter, Section section, Api api, String docId) {
        ApiEntity apiEntity = new ApiEntity();
        BeanUtils.copyProperties(api, apiEntity);
        apiEntity.setId(UUIDUtils.generateUUID());
        apiEntity.setDocId(docId);
        apiEntity.setCates(Lists.newArrayList(chapter.getName(), section.getName()));
        apiEntity.setSite("fdd");
        bulkWriteList.add(new InsertOneModel<>(apiEntity));
    }
}
