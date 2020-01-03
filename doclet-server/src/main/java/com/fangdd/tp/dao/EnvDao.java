package com.fangdd.tp.dao;

import com.fangdd.tp.doclet.pojo.entity.Env;
import org.springframework.stereotype.Repository;

/**
 * @author xuwenzhen
 * @date 18/1/23
 */
@Repository
public class EnvDao extends BaseDuoDocEntityDao<Env> {
    @Override
    protected String getCollectionName() {
        return "env";
    }
}
