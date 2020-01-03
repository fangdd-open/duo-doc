package com.fangdd.tp.dao;

import com.fangdd.traffic.common.mongo.core.BaseEntityDao;

/**
 * @author xuwenzhen
 * @date 18/1/23
 */
public abstract class BaseDuoDocEntityDao<T> extends BaseEntityDao<T> {
    @Override
    protected String getDatabaseName() {
        return "tp_doc";
    }

    @Override
    protected String getMongoClientName() {
        return "yMongoClient";
    }
}
