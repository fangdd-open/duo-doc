package com.fangdd.tp.controller.api;

import com.fangdd.tp.doclet.pojo.entity.Env;
import com.fangdd.tp.dto.BaseResponse;
import com.fangdd.tp.dto.request.EnvSaveDto;
import com.fangdd.tp.service.EvnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @chapter 文档接口
 * @section 环境接口
 * @auth ycoe
 * @date 18/10/26
 */
@RestController
@RequestMapping("/api/env")
public class EnvApiController {
    @Autowired
    private EvnService evnService;

    /**
     * 保存项目环境
     *
     * @param request 环境请求
     * @return
     */
    @PostMapping
    public BaseResponse<Env> save(@RequestBody EnvSaveDto request) {
        Env env = evnService.save(request);
        return BaseResponse.success(env);
    }

    /**
     * 获取项目环境
     *
     * @param id 项目ID
     * @return
     */
    @GetMapping("/{id}")
    public BaseResponse<Env> get(@PathVariable String id) {
        Env env = evnService.get(id);
        return BaseResponse.success(env);
    }
}
