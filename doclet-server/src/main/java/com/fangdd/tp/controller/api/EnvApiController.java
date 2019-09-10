package com.fangdd.tp.controller.api;

import com.fangdd.tp.core.annotation.Account;
import com.fangdd.tp.doclet.pojo.entity.Env;
import com.fangdd.tp.service.EvnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @chapter 文档接口
 * @section 环境接口
 * @author xuwenzhen
 * @date 18/10/26
 * @disable
 */
@RestController
@RequestMapping("/api/env")
public class EnvApiController {
    @Autowired
    private EvnService evnService;

    /**
     * 获取项目环境
     *
     * @param id 项目ID
     * @return
     */
    @GetMapping("/{id}")
    public Env get(@PathVariable String id) {
        return evnService.get(id);
    }

    @Account
    @PostMapping
    public boolean save(@RequestBody Env env) {
        return evnService.save(env);
    }
}
