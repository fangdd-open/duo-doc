package com.fangdd.tp.service;

import com.fangdd.tp.dto.request.EnvSaveDto;
import com.fangdd.tp.doclet.pojo.entity.Env;

/**
 * @auth ycoe
 * @date 18/10/26
 */
public interface EvnService {
    /**
     * 保存环境
     *
     * @param request 环境变量
     * @return
     */
    Env save(EnvSaveDto request);

    /**
     * 获取某个项目的环境
     *
     * @param id 项目key: {groupId}:{artifactId}
     * @return 环境变量
     */
    Env get(String id);
}
