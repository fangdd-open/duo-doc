package com.fangdd.tp.service.impl;

import com.fangdd.tp.core.exceptions.DocletException;
import com.fangdd.tp.dao.EnvDao;
import com.fangdd.tp.doclet.enums.EnvEnum;
import com.fangdd.tp.doclet.pojo.entity.Env;
import com.fangdd.tp.doclet.pojo.entity.EnvItem;
import com.fangdd.tp.doclet.pojo.entity.RequestParam;
import com.fangdd.tp.service.EvnService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @auth ycoe
 * @date 18/10/26
 */
@Service
public class EvnServiceImpl implements EvnService {
    @Autowired
    private EnvDao envDao;

    @Override
    public boolean save(Env request) {
        String id = request.getId();
        if (Strings.isNullOrEmpty(id)) {
            throw new DocletException("id不能为空！");
        }

        List<String> dubboEnvs = request.getDubboEnvs();

        List<EnvItem> restEnvs = request.getRestEnvs();
        if (restEnvs == null || restEnvs.isEmpty()) {
            throw new DocletException("项目环境不能为空！");
        }

        List<EnvItem> restItems = Lists.newArrayList();
        restEnvs.forEach(envItem -> {
            EnvItem item = getEnvItem(envItem);
            if (item == null) {
                return;
            }
            restItems.add(item);
        });

        Env env = get(id);
        if (env == null) {
            //新增
            env = new Env();
            env.setId(id);
            env.setRestEnvs(restItems);
            env.setRestTest(request.getRestTest());
            if (!CollectionUtils.isEmpty(dubboEnvs)) {
                env.setDubboEnvs(dubboEnvs);
            }
            envDao.insertOne(env);
        } else {
            env.setRestEnvs(restItems);
            env.setRestTest(request.getRestTest());
            if (!CollectionUtils.isEmpty(dubboEnvs)) {
                env.setDubboEnvs(dubboEnvs);
            } else {
                env.setDubboEnvs(null);
            }
            envDao.updateEntity(env);
        }
        return true;
    }

    private EnvItem getEnvItem(EnvItem envItem) {
        String url = envItem.getUrl();
        if (Strings.isNullOrEmpty(url)) {
            return null;
        }
        String code = envItem.getCode();
        if (Strings.isNullOrEmpty(code)) {
            throw new DocletException("项目环境代码不能为空！");
        }

        EnvEnum envEnum = EnvEnum.valueOf(code.toUpperCase());
        EnvItem item = new EnvItem();
        item.setCode(envEnum.name());
        item.setName(envItem.getName());
        item.setUrl(url);
        if (!CollectionUtils.isEmpty(envItem.getHeaders())) {
            List<RequestParam> headers = Lists.newArrayList();
            envItem.getHeaders()
                    .stream()
                    .filter(header -> !Strings.isNullOrEmpty(header.getKey()))
                    .forEach(header -> {
                        RequestParam h = new RequestParam();
                        h.setKey(header.getKey());
                        h.setValue(header.getValue());
                        h.setDescription(header.getDescription());
                        headers.add(h);
                    });
            if (!headers.isEmpty()) {
                item.setHeaders(headers);
            }
        }
        return item;
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
