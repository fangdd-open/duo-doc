package com.fangdd.tp.service.impl;

import com.fangdd.tp.dao.*;
import com.fangdd.tp.doclet.pojo.*;
import com.fangdd.tp.doclet.pojo.entity.DocLog;
import com.fangdd.tp.doclet.pojo.entity.MarkdownDoc;
import com.fangdd.tp.service.DocImportService;
import com.fangdd.traffic.common.mongo.utils.UUIDUtils;
import com.google.common.collect.Lists;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertOneModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @auth ycoe
 * @date 18/1/23
 */
@Service
public class DocImportServiceImpl implements DocImportService {
    @Autowired
    private DocEntityDao docEntityDao;

    @Autowired
    private DocDao docDao;

    @Autowired
    private DocChapterDao docChapterDao;

    @Autowired
    private DocLogDao docLogDao;

    @Autowired
    private MarkdownDocDao markdownDocDao;

    @Override
    public String save(DocDto docRequest) {
        Artifact artifact = docRequest.getArtifact();
        artifact.setDocVersion(System.currentTimeMillis());
        List<Chapter> chapters = docRequest.getChapters();
        List<Entity> entities = docRequest.getEntities();
        Map<String, String> markdownDocMaps = docRequest.getMarkdownMaps();

        String md5 = artifact.getMd5();
        //检查是否一致
        if (docDao.exists(Filters.and(
                Filters.eq("_id", artifact.getId()),
                Filters.eq("md5", md5)
        ))) {
            return "/doc/" + artifact.getId() + "/";
        }

        //保存entitySet
        List bulkWriteList = Lists.newArrayList();
        entities.forEach(entity -> addBulkWriteList(artifact, bulkWriteList, entity));

        docEntityDao.bulkWrite(bulkWriteList);
        bulkWriteList.clear();

        //保存chapter
        chapters.forEach(chapter -> addBulkWriteList(artifact, bulkWriteList, chapter));

        //保存markdown文档
        if (markdownDocMaps != null && !markdownDocMaps.isEmpty()) {
            List markdownDocBulkWriteList = Lists.newArrayList();
            for (Map.Entry<String, String> markdown : markdownDocMaps.entrySet()) {
                saveMarkdownDoc(artifact, markdown.getKey(), markdown.getValue(), markdownDocBulkWriteList);
            }
            if (!markdownDocBulkWriteList.isEmpty()) {
                markdownDocDao.bulkWrite(markdownDocBulkWriteList);
            }
        }

        docChapterDao.bulkWrite(bulkWriteList);
        bulkWriteList.clear();

        //写入日志
        DocLog log = new DocLog();
        log.setDocId(artifact.getId());
        BeanUtils.copyProperties(artifact, log);
        log.setId(UUIDUtils.generateUUID());
        docLogDao.insertOne(log);

        //写入doc
        docDao.upsertEntity(artifact);

        return "/doc/" + artifact.getId() + "/";
    }

    private void saveMarkdownDoc(Artifact artifact, String docName, String markdownStr, List markdownDocBulkWriteList) {
        MarkdownDoc doc = new MarkdownDoc();
        doc.setId(UUIDUtils.generateUUID());
        doc.setName(docName);
        doc.setId(UUIDUtils.generateUUID());
        doc.setDocId(artifact.getId());
        doc.setDocVersion(artifact.getDocVersion());
        doc.setMarkdown(markdownStr);
        markdownDocBulkWriteList.add(new InsertOneModel<>(doc));
    }

    private void addBulkWriteList(Artifact artifact, List bulkWriteList, MongoDbEntity entity) {
        entity.setId(UUIDUtils.generateUUID());
        entity.setDocId(artifact.getId());
        entity.setDocVersion(artifact.getDocVersion());
        bulkWriteList.add(new InsertOneModel<>(entity));
    }
}
