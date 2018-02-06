package com.fangdd.tp.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.fangdd.tp.doclet.pojo.Artifact;
import com.fangdd.tp.doclet.pojo.DocDto;
import com.fangdd.tp.doclet.pojo.entity.DocLog;
import com.fangdd.tp.dto.BaseResponse;
import com.fangdd.tp.dto.request.DocLogQuery;
import com.fangdd.tp.dto.request.DocQuery;
import com.fangdd.tp.helper.GzipHelper;
import com.fangdd.tp.helper.MD5Utils;
import com.fangdd.tp.service.DocImportService;
import com.fangdd.tp.service.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @chapter Rest接口
 * @section 文档接口
 * @auth ycoe
 * @date 18/1/18
 */
@RestController
@RequestMapping("/api/doc")
public class DocApiController {
    @Autowired
    private DocImportService docImportService;

    @Autowired
    private DocService docService;

    /**
     * 提交文档信息
     * 是由tp-doc触发的
     *
     * @param request 文档请求
     * @return 可访问的文档URL
     */
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse<String> save(@RequestBody byte[] request) {
        String docStr;
        try {
            docStr = GzipHelper.decompress(request);
        } catch (IOException e) {
            return BaseResponse.error(501, "发生未知错误！");
        }

        String md5 = MD5Utils.md5(docStr);
        DocDto docRequest = JSONObject.parseObject(docStr, DocDto.class);
        docRequest.getArtifact().setMd5(md5);
        return BaseResponse.success(docImportService.save(docRequest));
    }

    /**
     * 获取某个文档数据，用于构建文档
     *
     * @param id      文档ID
     * @param version 文档版本
     * @return 文档的全部数据
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public DocDto get(
            @PathVariable String id,
            @RequestParam(required = false) Long version
    ) {
        return docService.get(id, version);
    }

    /**
     * 获取所有的文档
     *
     * @param query 查询条件
     * @return 文档列表
     */
    @RequestMapping(method = RequestMethod.GET, value = "/docs")
    public List<Artifact> listDoc(DocQuery query) {
        return docService.getDocList(query);
    }

    /**
     * 获取某个文档的所有变更历史
     *
     * @param query 查询条件
     * @return 文档的变更列表
     */
    @RequestMapping(method = RequestMethod.GET, value = "/doc-logs")
    public List<DocLog> listDocLogs(DocLogQuery query) {
        return docService.getDocLogList(query);
    }
}
