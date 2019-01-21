package com.fangdd.tp.dao;

import com.fangdd.tp.doclet.pojo.entity.DocLog;
import org.springframework.stereotype.Repository;

/**
 * @auth ycoe
 * @date 18/1/23
 */
@Repository
public class DocLogDao extends TpDocBaseEntityDao<DocLog> {
    @Override
    protected String getCollectionName() {
        return "doc_log";
    }
}
