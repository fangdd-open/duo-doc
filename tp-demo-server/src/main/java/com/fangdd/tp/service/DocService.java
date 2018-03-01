package com.fangdd.tp.service;

import com.fangdd.tp.doclet.pojo.Artifact;
import com.fangdd.tp.doclet.pojo.DocDto;
import com.fangdd.tp.doclet.pojo.entity.DocLog;
import com.fangdd.tp.dto.request.DocLogQuery;
import com.fangdd.tp.dto.request.DocQuery;

import java.util.List;

/**
 * @auth ycoe
 * @date 18/1/23
 */
public interface DocService {
    /**
     * 获取文档
     *
     * @param id      文档ID
     * @param version 文档版本，未指定则使用最新的文档
     * @return
     */
    DocDto get(String id, Long version);

    /**
     * 查询某些文档日志
     *
     * @param query 查询条件
     * @return
     */
    List<DocLog> getDocLogList(DocLogQuery query);

    /**
     * 查询文档列表
     *
     * @param query 查询条件
     * @return
     */
    List<Artifact> getDocList(DocQuery query);

    /**
     * 获取文档基本信息
     *
     * @param docId 文档ID
     * @return
     */
    Artifact getDocArtifact(String docId);

    /**
     * 获取某个文档历史版本总数
     *
     * @param docId 文档ID
     * @return
     */
    int getTotal(String docId);

    /**
     * 删除某个历史文档
     *
     * @param id      文档ID
     * @param version 文档版本{docVersion}
     * @return
     */
    String delete(String id, long version);
}
