package com.fangdd.tp.service.impl;

import com.fangdd.tp.dao.*;
import com.fangdd.tp.doclet.pojo.*;
import com.fangdd.tp.doclet.pojo.entity.DocLog;
import com.fangdd.tp.doclet.pojo.entity.MarkdownDoc;
import com.fangdd.tp.dto.request.DocCreateDto;
import com.fangdd.tp.helper.UserContextHelper;
import com.fangdd.tp.service.ApiUnwindService;
import com.fangdd.tp.service.DocImportService;
import com.fangdd.tp.service.UserLogService;
import com.fangdd.traffic.common.mongo.utils.UUIDUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.Projections;
import org.bson.conversions.Bson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xuwenzhen
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

    @Autowired
    private ApiUnwindService apiUnwindService;

    @Autowired
    private UserLogService userLogService;

    @Override
    public String save(DocDto docRequest) {
        Artifact artifact = docRequest.getArtifact();
        long docVersion = System.currentTimeMillis();
        artifact.setDocVersion(docVersion);
        List<Chapter> chapters = docRequest.getChapters();
        List<Entity> entities = docRequest.getEntities();
        Map<String, String> markdownDocMaps = docRequest.getMarkdownMaps();

        String md5 = artifact.getMd5();
        if (Strings.isNullOrEmpty(md5)) {
            return "ERROR: 版本过低，请更新包：com.fangdd:tp-doc:1.2.0";
        }

        //检查是否一致
        Bson idFilter = Filters.eq("_id", artifact.getId());
        Artifact existArtifact = docDao
                .find(idFilter)
                .projection(Projections.include("md5"))
                .first();
        if (existArtifact != null) {
            if (md5.equals(existArtifact.getMd5())) {
                return "/doc/" + artifact.getId() + "/";
            }
        } else {
            // 不存在，创建新文档
            DocCreateDto createDto = new DocCreateDto();
            createDto.setSite(UserContextHelper.getSite().getId());
            createDto.setDocId(artifact.getId());
            createDto.setDocVersion(docVersion);
            this.userLogService.add(createDto);
        }

        //保存entitySet
        List bulkWriteList = Lists.newArrayList();
        entities.forEach(entity -> addBulkWriteList(artifact, bulkWriteList, entity));

        if (!bulkWriteList.isEmpty()) {
            docEntityDao.bulkWrite(bulkWriteList);
            bulkWriteList.clear();
        }

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

        if (!bulkWriteList.isEmpty()) {
            docChapterDao.bulkWrite(bulkWriteList);
            bulkWriteList.clear();
        }

        //写入日志
        DocLog log = new DocLog();
        log.setDocId(artifact.getId());
        BeanUtils.copyProperties(artifact, log);
        log.setId(UUIDUtils.generateUUID());
        docLogDao.insertOne(log);

        //写入doc
        docDao.upsertEntity(artifact);

        //unwind
        apiUnwindService.unwindDocApi(artifact.getId(), artifact.getDocVersion());

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
