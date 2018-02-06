package com.fangdd.tp.dao;

import com.fangdd.tp.doclet.pojo.Entity;
import org.springframework.stereotype.Repository;

/**
 * @auth ycoe
 * @date 18/1/23
 */
@Repository
public class DocEntityDao extends TpDocBaseEntityDao<Entity> {
    @Override
    protected String getCollectionName() {
        return "doc_entity";
    }
}
