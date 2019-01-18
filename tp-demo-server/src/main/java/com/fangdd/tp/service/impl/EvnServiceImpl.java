package com.fangdd.tp.service.impl;

import com.fangdd.tp.core.exceptions.DocletException;
import com.fangdd.tp.dao.EnvDao;
import com.fangdd.tp.doclet.enums.EnvEnum;
import com.fangdd.tp.doclet.pojo.entity.Env;
import com.fangdd.tp.doclet.pojo.entity.EnvItem;
import com.fangdd.tp.dto.request.EnvSaveDto;
import com.fangdd.tp.service.EvnService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.client.model.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auth ycoe
 * @date 18/10/26
 */
@Service
public class EvnServiceImpl implements EvnService {
    @Autowired
    private EnvDao envDao;

    /**
     * 保存环境
     *
     * @param request 环境变量
     * @return
     */
    @Override
    public Env save(EnvSaveDto request) {
        String id = request.getId();
        if (Strings.isNullOrEmpty(id)) {
            throw new DocletException("id不能为空！");
        }

        List<EnvItem> envs = request.getEnvs();
        if (envs == null || envs.isEmpty()) {
            throw new DocletException("项目环境不能为空！");
        }

        List<EnvItem> items = Lists.newArrayList();
        envs.forEach(envItem -> {
            String url = envItem.getUrl();
            if (Strings.isNullOrEmpty(url)) {
                return;
            }
            String code = envItem.getCode();
            if (Strings.isNullOrEmpty(code)) {
                throw new DocletException("项目环境代码不能为空！");
            }

            EnvEnum envEnum = EnvEnum.valueOf(code.toLowerCase());
            EnvItem item = new EnvItem();
            item.setCode(envEnum.name());
            item.setName(envEnum.getName());
            item.setUrl(url);
            items.add(item);
        });
        if (items.isEmpty()) {
            envDao.deleteOne(Filters.eq("_id", id));
            return null;
        }

        Env env = get(id);
        if (env == null) {
            //新增
            env = new Env();
            env.setId(id);
            env.setRestEnvs(items);
            envDao.insertOne(env);
        } else {
            envDao.updateEntity(env);
        }

        return env;
    }

    /**
     * 获取某个项目的环境
     *
     * @param id 项目key: {groupId}:{artifactId}
     * @return 环境变量
     */
    @Override
    public Env get(String id) {
        return envDao.getEntityById(id);
    }
}
