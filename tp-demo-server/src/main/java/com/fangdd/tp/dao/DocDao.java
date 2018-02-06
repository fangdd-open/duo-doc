package com.fangdd.tp.dao;

import com.fangdd.tp.doclet.pojo.Artifact;
import org.springframework.stereotype.Repository;

/**
 * @auth ycoe
 * @date 18/1/23
 */
@Repository
public class DocDao extends TpDocBaseEntityDao<Artifact> {
    @Override
    protected String getCollectionName() {
        return "doc";
    }
}
