package com.fangdd.tp.service;

import com.fangdd.tp.doclet.pojo.entity.Env;

/**
 * @author ycoe
 * @date 18/10/26
 */
public interface EvnService {
    /**
     * 保存环境
     *
     * @param request 环境变量
     * @return
     */
    boolean save(Env request);

    /**
     * 获取某个项目的环境
     *
     * @param id 项目key: {groupId}:{artifactId}
     * @return 环境变量
     */
    Env get(String id);
}
