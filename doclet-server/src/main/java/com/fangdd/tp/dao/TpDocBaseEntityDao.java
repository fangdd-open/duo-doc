package com.fangdd.tp.dao;

import com.fangdd.traffic.common.mongo.core.BaseEntityDao;
import com.fangdd.traffic.common.mongo.core.YMongoClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xuwenzhen
 * @date 18/1/23
 */
public abstract class TpDocBaseEntityDao<T> extends BaseEntityDao<T> {
    @Autowired
    private YMongoClient mongoClient;

    @Override
    protected String getDatabaseName() {
        return "tp_doc";
    }

    /**
     * 在spring中注册的YMongoClient类名称
     *
     * @return
     */
    @Override
    protected String getMongoClientName() {
        return "yMongoClient";
    }
}
