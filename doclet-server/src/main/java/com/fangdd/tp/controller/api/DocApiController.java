package com.fangdd.tp.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.fangdd.tp.core.DocletException;
import com.fangdd.tp.doclet.pojo.Artifact;
import com.fangdd.tp.doclet.pojo.DocDto;
import com.fangdd.tp.doclet.pojo.ProviderApiDto;
import com.fangdd.tp.doclet.pojo.entity.DocLog;
import com.fangdd.tp.dto.BaseResponse;
import com.fangdd.tp.dto.request.DocLogQuery;
import com.fangdd.tp.dto.request.DocQuery;
import com.fangdd.tp.dto.response.SimpleUserDto;
import com.fangdd.tp.helper.GzipHelper;
import com.fangdd.tp.helper.MD5Utils;
import com.fangdd.tp.service.ApiUnwindService;
import com.fangdd.tp.service.DocImportService;
import com.fangdd.tp.service.DocService;
import com.fangdd.tp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author xuwenzhen
 * @chapter Rest接口
 * @section 文档接口
 * @date 18/1/18
 * @disable
 */
@RestController
@RequestMapping("/api/doc")
public class DocApiController {
    private static final Logger logger = LoggerFactory.getLogger(DocApiController.class);

    @Autowired
    private DocImportService docImportService;

    @Autowired
    private DocService docService;

    @Autowired
    private ApiUnwindService apiUnwindService;

    @Autowired
    private UserService userService;

    /**
     * 提交文档信息
     * 是由tp-doc触发的
     *
     * @param request 文档请求
     * @return 可访问的文档URL
     */
    @PostMapping
    public BaseResponse<String> save(HttpServletRequest request) {
        int len = request.getContentLength();
        byte[] buffer = new byte[len];
        try (ServletInputStream iii = request.getInputStream()) {
            iii.read(buffer, 0, len);
        } catch (Exception e) {
            logger.error("读取请求体失败！", e);
        }

        String docStr;
        try {
            docStr = GzipHelper.decompress(buffer);
        } catch (IOException e) {
            logger.error("gzip解压失败！", e);
            return BaseResponse.error(501, "gzip解压失败！");
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
    @GetMapping("/{id}")
    public DocDto get(
            @PathVariable String id,
            @RequestParam(required = false) Long version
    ) {
        return docService.get(id, version);
    }

    /**
     * 通过appId获取某个文档数据
     *
     * @param appId    appId
     * @param vcsId    版本ID
     * @param apiCodes 需要拉取的接口
     * @return 文档的全部数据
     */
    @GetMapping("/app/{appId}")
    public ResponseEntity<byte[]> get(
            @PathVariable String appId,
            @RequestParam(required = false) String vcsId,
            @RequestParam(required = false) String apiCodes,
            HttpServletResponse response
    ) {
        ProviderApiDto providerApiDto = docService.getByAppId(appId, vcsId);

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        if (providerApiDto == null) {
            return new ResponseEntity<>(new byte[0], headers, HttpStatus.NOT_FOUND);
        }
        try {
            byte[] compressedBytes = GzipHelper.compress(JSONObject.toJSONString(providerApiDto));
            return new ResponseEntity<>(compressedBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new DocletException("压缩失败！", e);
        }
    }

    /**
     * 查询某文档版本号是否最新
     *
     * @param id      文档ID
     * @param version 文档版本
     * @return 是最新，则返回空，否则返回当前最新的文档
     */
    @GetMapping("/fetch/{id}")
    public DocDto testFetch(
            @PathVariable String id,
            @RequestParam(required = false) Long version
    ) {
        return docService.fetch(id, version);
    }

    /**
     * 获取某个文档的所有版本列表
     *
     * @param query 查询条件
     * @return 此文档的所有版本信息
     */
    @PostMapping("/history")
    public List<DocLog> listVersion(
            @RequestBody DocLogQuery query
    ) {
        return docService.getDocLogList(query);
    }

    /**
     * 获取所有的文档
     *
     * @param query 查询条件
     * @return 文档列表
     */
    @GetMapping("/docs")
    public List<Artifact> listDoc(DocQuery query) {
        return docService.getDocList(query);
    }

    /**
     * 检查文档是否有更新
     *
     * @param v 最新的文档版本
     * @return 文档列表
     */
    @GetMapping("/docs-check")
    public List<Artifact> listDocCheck(@RequestParam long v) {
        return docService.getDocListCheck(v);
    }

    /**
     * 获取某个文档的所有变更历史
     *
     * @param query 查询条件
     * @return 文档的变更列表
     */
    @GetMapping("/doc-logs")
    public List<DocLog> listDocLogs(DocLogQuery query) {
        return docService.getDocLogList(query);
    }

    /**
     * 删除某个历史文档
     *
     * @param id      查询条件
     * @param version 版本号
     * @return
     */
    @DeleteMapping("/{id}/")
    public String del(
            @PathVariable String id,
            @RequestParam long version
    ) {
        return docService.delete(id, version);
    }

    @GetMapping("/api-unwind")
    public String unwind() {
        apiUnwindService.unwindAll();
        return "success";
    }

    @GetMapping("/{docId}/owner")
    public List<SimpleUserDto> getDocOwner(@PathVariable String docId) {
        return userService.getOwners(docId);
    }
}
