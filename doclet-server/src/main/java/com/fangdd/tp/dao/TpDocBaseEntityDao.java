package com.fangdd.tp.dao;

import com.fangdd.traffic.common.mongo.core.BaseEntityDao;
import com.fangdd.traffic.common.mongo.core.YMongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author ycoe
 * @date 18/1/23
 */
public abstract class TpDocBaseEntityDao<T> extends BaseEntityDao<T> {
    @Autowired
    @Qualifier("cmsMongoClient")
    private YMongoClient yMongoClient;

    @Override
    protected String getDatabaseName() {
        return "tp_doc";
    }

    @Override
    protected YMongoClient getYMongoClient() {
        return yMongoClient;
    }
}
